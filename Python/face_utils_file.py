import math
from imutils import face_utils
from imutils.face_utils.helpers import FACIAL_LANDMARKS_IDXS
import numpy as np
import imutils
import dlib
import cv2
from scipy.spatial import distance as dist
from mouth_open_algorithm import get_lip_height, get_mouth_height
import pickle

detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor("face_utils/shape_predictor_68_face_landmarks.dat")
MOUTH_AR_THRESH = 0.74
with open('facial_expression_recognition/facial_expression_recognition.pkl', 'rb') as f:
    model = pickle.load(f)
face_cascade = cv2.CascadeClassifier("facial_expression_recognition/haarcascade_frontalface_default.xml")


def calculating_mouth_open(shape,faceAligned):
    (mStart, mEnd) = (49, 56)
    top_lip1 = shape[mStart:mEnd]
    (mStart, mEnd) = (60, 65)  # Adjusted values of mStart and mEnd
    top_lip2 = shape[mStart:mEnd][::-1]
    top_lip = np.concatenate((top_lip1, top_lip2))
    (mStart, mEnd) = (55, 61)
    bottom_lip1 = shape[mStart:mEnd]
    (mStart, mEnd) = (49, 50)
    bottom_lip2 = shape[mStart:mEnd]
    (mStart, mEnd) = (61, 62)
    bottom_lip3 = shape[mStart:mEnd]
    (mStart, mEnd) = (64, 68)
    bottom_lip4 = shape[mStart:mEnd][::-1]
    bottom_lip = np.concatenate((bottom_lip1, bottom_lip2))
    bottom_lip = np.concatenate((bottom_lip, bottom_lip3))
    bottom_lip = np.concatenate((bottom_lip, bottom_lip4))
    top_lip_height = get_lip_height(top_lip)
    bottom_lip_height = get_lip_height(bottom_lip)
    mouth_height = get_mouth_height(top_lip, bottom_lip)

    # if mouth is open more than lip height * ratio, return true.
    ratio = 0.5
    # print('top_lip_height: %.2f, bottom_lip_height: %.2f, mouth_height: %.2f, min*ratio: %.2f'
    #       % (top_lip_height, bottom_lip_height, mouth_height, min(top_lip_height, bottom_lip_height) * ratio))
    if machine_learning_smile(faceAligned):
        return (True, mouth_height)
    if mouth_height > min(top_lip_height, bottom_lip_height) * ratio:
        return (True , mouth_height)
    else:
        return (False, mouth_height)


def get_lips_distance(shape):
    left_eye_points = list(range(36, 42))
    right_eye_points = list(range(42, 48))
    left_eye_mean = np.mean([shape[i] for i in left_eye_points], axis=0)
    right_eye_mean = np.mean([shape[i] for i in right_eye_points], axis=0)
    lips_distance = np.linalg.norm(left_eye_mean - right_eye_mean)
    return lips_distance


def lips_contracted(shape):
    lips_distance = get_lips_distance(shape)


def make_prediction(unknown):
    unknown=cv2.resize(unknown,(48,48))
    unknown=unknown/255.0
    unknown=np.array(unknown).reshape(-1,48,48,1)
    predict=np.argmax(model.predict(unknown),axis = 1)
    return predict[0]


def machine_learning_smile(frame):
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    for (x, y, w, h) in faces:
        sub_face = gray[y:y + h, x:x + w]
        cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)
        res = make_prediction(sub_face)
        if res == 3:
            return True
    return False


def machine_learning_open(frame):
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    for (x, y, w, h) in faces:
        sub_face = gray[y:y + h, x:x + w]
        cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)
        res = make_prediction(sub_face)
        if res == 5:
            return True
    return False


def lip_width_image(shape):
    (mStart, mEnd) = (49, 68)
    mouth = shape[mStart:mEnd]
    x49 = mouth[0][0]
    y49 = mouth[0][1]
    x55 = mouth[6][0]
    y55 = mouth[6][1]
    dist_smilo = 0
    dist_smile = ((x49 - x55) ** 2 + (y49 - y55) ** 2) ** 0.5
    diff_smile = (dist_smile) - dist_smilo
    if diff_smile < 0:
        diff_smile *= -1
    return diff_smile


def calculating_lip_pursing(shape,faceAligned,lip_width):
    ret_smile, diff_smile = calculating_smile(shape,faceAligned,lip_width)
    error_range_lip_pursing = 4
    mouth_height_error = 25
    # 84
    if (lip_width >= 80 and lip_width < 90):
        error_range_smile = 5
        error_range_lip_pursing = error_range_smile - 3
        mouth_height_error = 25

    elif (lip_width >= 70 and lip_width < 80):
        error_range_smile = 3
        error_range_lip_pursing = error_range_smile - 2
        mouth_height_error = 20

    elif lip_width >= 90:
        error_range_smile = 5
        error_range_lip_pursing = error_range_smile - 2
        mouth_height_error = 30
    ret_mouth_open, mouth_height = calculating_mouth_open(shape, faceAligned)
    if diff_smile < (lip_width - error_range_lip_pursing) and mouth_height < mouth_height_error:
        return True,diff_smile
    else:
        return False, diff_smile



def calculating_smile(shape,faceAligned,lip_width):
    ret_mouth_open, mouth_height = calculating_mouth_open(shape,faceAligned)
    error_range_smile = 4
    mouth_height_error = 25
    if (lip_width >= 80 and lip_width < 90):
        error_range_smile = 5
        mouth_height_error = 25
    elif (lip_width >= 70 and lip_width < 80):
        error_range_smile = 3
        mouth_height_error = 20

    elif lip_width >= 90:
        error_range_smile = 5
        mouth_height_error = 30
    (mStart, mEnd) = (49, 68)
    mouth = shape[mStart:mEnd]
    x49 = mouth[0][0]
    y49 = mouth[0][1]
    x55 = mouth[6][0]
    y55 = mouth[6][1]
    dist_smilo = 0
    dist_smile = ((x49 - x55) ** 2 + (y49 - y55) ** 2) ** 0.5
    diff_smile = (dist_smile) - dist_smilo

    if diff_smile < 0:
        diff_smile *= -1
    if machine_learning_smile(faceAligned):
        return True, diff_smile
    if diff_smile > (lip_width + error_range_smile) and mouth_height < mouth_height_error:
        return True, diff_smile
    else:
        return False, diff_smile


def shape_to_np(shape, dtype="int"):
    # initialize the list of (x, y)-coordinates
    coords = np.zeros((68, 2), dtype=dtype)
    # loop over the 68 facial landmarks and convert them
    # to a 2-tuple of (x, y)-coordinates
    for i in range(0, 68):
        coords[i] = (shape.part(i).x, shape.part(i).y)
    # return the list of (x, y)-coordinates
    return coords


# def get_rotated_mouth_loc_with_height(image):
#     x1, y1, w1, h1, h_in, y = 1, 1, 1, 1, 1, 1
#     image = imutils.resize(image, width=500)
#     gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
#     # detect faces in the grayscale image
#     rects = detector(gray, 1)
#     if (len(rects) > 0):
#         # loop over the face detections
#         for (i, rect) in enumerate(rects):
#             # determine the facial landmarks for the face region, then
#             # convert the landmark (x, y)-coordinates to a NumPy array
#             shape1 = predictor(gray, rect)
#             shape = shape_to_np(shape1)
#             x_lowest_in_face, y_lowest_in_face = shape[9]
#
#             # loop over the face parts individually
#             for (name, (i, j)) in face_utils.FACIAL_LANDMARKS_IDXS.items():
#
#                 if (name == "mouth"):
#                     # extract the ROI of the face region as a separate image
#                     mouth_rect = cv2.minAreaRect(np.array([shape[i:j]]))
#                 # mouth_box = cv2.boxPoints(rect)
#                 # (x1, y1, w1, h1) = cv2.boundingRect(np.array([shape[i:j]]))
#                 if (name == "inner_mouth"):
#                     # loop over the subset of facial landmarks, drawing the
#                     # specific face part
#                     # for (x, y) in shape[i:j]:
#                     # 	cv2.circle(clone, (x, y), 1, (0, 0, 255), -1)
#                     # extract the ROI of the face region as a separate image
#                     inner_mouth_rect = cv2.minAreaRect(np.array([shape[i:j]]))
#             # inner_mouth_box = cv2.boxPoints(rect)
#         return {"mouth_rect": mouth_rect,
#                 "inner_mouth_rect": inner_mouth_rect, "image_ret": image, "y_lowest_in_face": y_lowest_in_face,
#                 "shape": shape}
#     else:
#         return {"error": "true", "message": "No Face Found!"}

def progress_mouth_open(mouth_height, mouth_height_image):
    if mouth_height > mouth_height_image:
        return True
    else:
        return False


def progress_smile(diff_smile, diff_smile_image):
    if diff_smile > diff_smile_image:
        return True
    else:
        return False


def progress_lip_pursing(diff_lip_pursing, diff_lip_pursing_image):
    if diff_lip_pursing < diff_lip_pursing_image:
        return True
    else:
        return False


def progress_mouth_close(mouth_height, mouth_height_image):
    if mouth_height < mouth_height_image:
        return True
    else:
        return False

def get_mouth_loc_with_height(image):

    detector = dlib.get_frontal_face_detector()
    predictor = dlib.shape_predictor("face_utils/shape_predictor_68_face_landmarks.dat")
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # Detect faces in the image using the detector
    faces = detector(gray, 0)

    # Loop over each detected face
    for rect in faces:

        # Get the face landmarks using the predictor
        shape1 = predictor(gray, rect)

        # Extract the face encoding from the landmarks
        face_encoding = np.array([])
        for i in range(68):
            x = shape1.part(i).x
            y = shape1.part(i).y
            face_encoding = np.append(face_encoding, [x, y])

        # Store the face location, face encoding, and face landmarks in lists
        face_locations = [(rect.top(), rect.right(), rect.bottom(), rect.left())]
        face_encodings = [face_encoding]
        face_landmarks_list = [shape1.parts()]

        # Compare the face encoding with the known encoding(s)
        match = []  # insert your comparison code here

    x1, y1, w1, h1, h_in, y = 1, 1, 1, 1, 1, 1
    image = imutils.resize(image, width=500)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # detect faces in the grayscale image
    rects = detector(gray, 1)
    if (len(rects) > 0):
        # loop over the face detections
        for (i, rect) in enumerate(rects):
            # determine the facial landmarks for the face region, then
            # convert the landmark (x, y)-coordinates to a NumPy array
            shape1 = predictor(gray, rect)
            shape = shape_to_np(shape1)
            x_lowest_in_face, y_lowest_in_face = shape[9]

            # loop over the face parts individually
            for (name, (i, j)) in face_utils.FACIAL_LANDMARKS_IDXS.items():

                if (name == "mouth"):
                    # extract the ROI of the face region as a separate image
                    (x1, y1, w1, h1) = cv2.boundingRect(np.array([shape[i:j]]))
                if (name == "inner_mouth"):
                    # loop over the subset of facial landmarks, drawing the
                    # specific face part
                    # for (x, y) in shape[i:j]:
                    # 	cv2.circle(clone, (x, y), 1, (0, 0, 255), -1)
                    # extract the ROI of the face region as a separate image
                    (x, y, w, h) = cv2.boundingRect(np.array([shape[i:j]]))
                    h_in = h
        return {"mouth_x": x1,
                "mouth_y": y1, "mouth_w": w1, "mouth_h": h1, "image_ret": image, "height_of_inner_mouth": h_in,
                "inner_mouth_y": y, "y_lowest_in_face": y_lowest_in_face, "shape": shape}
    else:
        return {"error": "true", "message": "No Face Found!"}


def draw_mouth(image, shape):
    # draw mouth points
    (j, k) = FACIAL_LANDMARKS_IDXS["mouth"]
    (r, d) = FACIAL_LANDMARKS_IDXS["left_eye"]
    (f, p) = FACIAL_LANDMARKS_IDXS["right_eye"]
    pts_mouth = shape[j:k]
    pts_left_eye = shape[r: d]
    pts_right_eye = shape[f: p]

    pts_else = shape[0:100]
    for (x, y) in pts_else:
        cv2.circle(image, (x, y), 3, (14, 14, 16), -1)
    for (x, y) in pts_mouth:
        cv2.circle(image, (x, y), 3, (0, 0, 255), -1)
    for (x, y) in pts_left_eye:
        cv2.circle(image, (x, y), 3, (0, 139, 41), -1)
    for (x, y) in pts_right_eye:
        cv2.circle(image, (x, y), 3, (0, 139, 41), -1)
    return image


def mouth_aspect_ratio(shape):
    (mStart, mEnd) = (49, 68)
    mouth = shape[mStart:mEnd]
    A = dist.euclidean(mouth[2], mouth[10])  # 51, 59
    B = dist.euclidean(mouth[4], mouth[8])  # 53, 57
    C = dist.euclidean(mouth[0], mouth[6])  # 49, 55
    mar = (A + B) / (2.0 * C)
    return mar


def mouth_aspect_ratio_open(shape):
    (mStart, mEnd) = (49, 68)
    mouth = shape[mStart:mEnd]
    A = dist.euclidean(mouth[12], mouth[18])  # 62, 68
    B = dist.euclidean(mouth[13], mouth[17])  # 63, 67
    C = dist.euclidean(mouth[14], mouth[16])  # 64, 66
    mar = (A + B + C) /3.0
    return mar