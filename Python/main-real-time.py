# import the necessary packages
from imutils import face_utils
from imutils.face_utils import rect_to_bb

from imutils.video import VideoStream
import imutils
import dlib
import cv2
import numpy as np
from matplotlib import pyplot as plt
import math

from face_utils_file import MOUTH_AR_THRESH, draw_mouth, get_mouth_loc_with_height, mouth_aspect_ratio, \
    mouth_aspect_ratio_open, lips_contracted, make_prediction, lip_width_image, calculating_smile, \
    calculating_mouth_open, calculating_lip_pursing, progress_mouth_open, progress_mouth_close, progress_smile, \
    progress_lip_pursing

# from smile import smile1

# initialize the video stream and allow the cammera sensor to warmup
print("[INFO] camera sensor warming up...")
vs = VideoStream(0).start()
def checkKey(dict, key):
    return key in dict.keys()
i = 0
predictor = dlib.shape_predictor(
        "face_utils/shape_predictor_68_face_landmarks.dat")
fa = face_utils.FaceAligner(predictor, desiredFaceWidth=500)
detector = dlib.get_frontal_face_detector()

opening = cv2.imread('opening.png')
close = cv2.imread('close.png')
smile_im = cv2.imread('smile.png')
pursing = cv2.imread('pursing.png')
image = cv2.imread('eynav.png')
# image = cv2.imread('elona.jpg')
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
rects = detector(gray, 2)
faceAligned = fa.align(image, gray, rects[0])
frame = faceAligned
enhanced = cv2.detailEnhance(frame, sigma_s=10, sigma_r=0.15)
frame = imutils.resize(frame, width=500)
result = get_mouth_loc_with_height(enhanced)
lip_width = 85
message = 'Face detected!'
if (checkKey(result, "error")):
    message = result['message']
    cv2.imshow("output", frame)
    cv2.putText(frame, message, (10, 30), cv2.FONT_HERSHEY_SIMPLEX,
                0.7, (0, 0, 0), 1)
else:
    mouth_x = result['mouth_x']
    mouth_y = result['mouth_y']
    mouth_w = result['mouth_w']
    mouth_h = result['mouth_h']
    image_ret = result['image_ret']
    height_of_inner_mouth = result['height_of_inner_mouth']
    inner_mouth_y = result['inner_mouth_y']
    y_lowest_in_face = result['y_lowest_in_face']
    shape = result['shape']
    frame = draw_mouth(frame, shape)
    lip_width = lip_width_image(shape)
    print("    print(lip_width)    ", lip_width)

while True:
    frame1 = vs.read()
    gray = cv2.cvtColor(frame1, cv2.COLOR_BGR2GRAY)
    rects = detector(gray, 2)
    faceAligned = fa.align(frame1, gray, rects[0])
    frame = faceAligned
    i = i + 1
    enhanced = cv2.detailEnhance(frame, sigma_s=10, sigma_r=0.15)
    frame = imutils.resize(frame, width=500)
    result = get_mouth_loc_with_height(enhanced)
    message = 'Face detected!'
    if (checkKey(result, "error")):
        message = result['message']
        cv2.imshow("output", frame)
        cv2.putText(frame, message, (10, 30), cv2.FONT_HERSHEY_SIMPLEX,
                    0.7, (0, 0, 0), 1)
        continue
    else:
        mouth_x = result['mouth_x']
        mouth_y = result['mouth_y']
        mouth_w = result['mouth_w']
        mouth_h = result['mouth_h']

        image_ret = result['image_ret']
        height_of_inner_mouth = result['height_of_inner_mouth']
        inner_mouth_y = result['inner_mouth_y']
        y_lowest_in_face = result['y_lowest_in_face']
        shape = result['shape']
        frame = draw_mouth(frame, shape)
        mouthMAR = mouth_aspect_ratio(shape)
        print("lip_width ",lip_width)
        ret_mouth_open, mouth_height = calculating_mouth_open(shape,faceAligned)
        ret_mouth_open_image, mouth_height_image = calculating_mouth_open(shape, faceAligned)
        progress_bool_open = progress_mouth_open(mouth_height,mouth_height_image)
        progress_bool_close = progress_mouth_close(mouth_height,mouth_height_image)
        print("open_mouth_height ",mouth_height)
        if ret_mouth_open is True:
            text = 'open mouth'
            cv2.putText(frame, "facial expression performed: " + text, (10, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                        (255, 255, 255), 1)
            cv2.putText(frame, "progress: "+str(progress_bool_open), (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)
        else:
            text = 'close mouth'
            cv2.putText(frame, "facial expression performed: " + text, (10, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                        (255, 255, 255), 1)

            cv2.putText(frame, "progress: "+str(progress_bool_close), (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)
        ret_smile, diff_smile = calculating_smile(shape,faceAligned,lip_width)
        ret_smile_image , diff_smile_image = calculating_smile(shape,faceAligned,lip_width)
        progress_bool = progress_smile(diff_smile,diff_smile_image)
        print("smile ",diff_smile)
        if ret_smile:
            cv2.putText(frame, "facial expression performed: " + "smile", (10, 80), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                        (255, 255, 255), 1)
            cv2.putText(frame, "progress: "+str(progress_bool), (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)
        ret_lip_pursing, diff_lip_pursing = calculating_lip_pursing(shape,faceAligned,lip_width)
        ret_lip_pursing_image, diff_lip_pursing_image = calculating_lip_pursing(shape,faceAligned,lip_width)
        progress_bool = progress_lip_pursing(diff_lip_pursing,diff_lip_pursing_image)

        if ret_lip_pursing:
            cv2.putText(frame, "facial expression performed: " + "pucker lips", (10, 80), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                        (255, 255, 255), 1)
            cv2.putText(frame, "progress: "+str(progress_bool), (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)

        cv2.imshow("output", frame)

    key = cv2.waitKey(1) & 0xFF
    # if the `q` key was pressed, break from the loop
    if key == ord("q"):
        break

# do a bit of cleanup
cv2.destroyAllWindows()
vs.stop()