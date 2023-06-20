from imutils import face_utils
import imutils
import dlib
import cv2
from FacialExpressions import FacialExpressions
from SmileAndPursing import SmileAndPursing
from OpenAndClose import OpenAndClose
import pickle


class AnalysisFrame:
    def __init__(self):
        predictor = dlib.shape_predictor(
            "face_utils/shape_predictor_68_face_landmarks.dat")
        self.fa = face_utils.FaceAligner(predictor, desiredFaceWidth=500)
        self.detector = dlib.get_frontal_face_detector()
        with open('face_utils/facial_expression_recognition.pkl', 'rb') as f:
            model = pickle.load(f)
        face_cascade = cv2.CascadeClassifier("face_utils/haarcascade_frontalface_default.xml")
        self.facial_expressions = FacialExpressions()
        self.smile_and_pursing = SmileAndPursing(model, face_cascade)
        self.open_and_close = OpenAndClose(model, face_cascade)


    def lip_width_image_get(self,file):
        image = cv2.imread(file)
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        rects = self.detector(gray, 2)
        faceAligned = self.fa.align(image, gray, rects[0])
        frame = faceAligned
        enhanced = cv2.detailEnhance(frame, sigma_s=10, sigma_r=0.15)
        frame = imutils.resize(frame, width=500)
        result = self.facial_expressions.get_mouth_loc_with_height(enhanced)
        lip_width = 85
        message = 'Face detected!'
        if (self.checkKey(result, "error")):
            message = result['message']
            cv2.imshow("output", frame)
            cv2.putText(frame, message, (10, 30), cv2.FONT_HERSHEY_SIMPLEX,
                        0.7, (0, 0, 0), 1)
        else:
            shape = result['shape']
            lip_width = self.facial_expressions.lip_width_image(shape)
        return lip_width


    def analysis_one_frame(self,frame1):
        gray = cv2.cvtColor(frame1, cv2.COLOR_BGR2GRAY)
        rects = self.detector(gray, 2)
        if len(rects) > 0:
            faceAligned = self.fa.align(frame1, gray, rects[0])
            frame = faceAligned
            enhanced = cv2.detailEnhance(frame, sigma_s=10, sigma_r=0.15)
            frame = imutils.resize(frame, width=500)
            result = self.facial_expressions.get_mouth_loc_with_height(enhanced)
            message = 'Face detected!'
            if (self.checkKey(result, "error")):
                message = result['message']
                cv2.imshow("output", frame)
                cv2.putText(frame, message, (10, 30), cv2.FONT_HERSHEY_SIMPLEX,
                            0.7, (0, 0, 0), 1)
                return
            else:
                shape = result['shape']
                frame = self.facial_expressions.draw_mouth(frame, shape)
                return shape, frame, faceAligned
                # print("lip_width ", self.lip_width)


    def analysis_facial_expressions(self,lip_width_normal, shape, frame, faceAligned):
        ret_mouth_open, mouth_height = self.open_and_close.calculating_mouth_open(shape, faceAligned)

        ret_mouth_close, mouth_height = self.open_and_close.calculating_mouth_close(shape, faceAligned)

        ret_mouth_open_image, mouth_height_image = self.open_and_close.calculating_mouth_open(shape,
                                                                                              faceAligned)
        progress_bool_open = self.open_and_close.progress_mouth_open(mouth_height, mouth_height_image)
        progress_bool_close = self.open_and_close.progress_mouth_close(mouth_height, mouth_height_image)
        ret_straight_face = self.facial_expressions.straight_face(shape)
        if ret_straight_face:
            print("open_mouth_height ", mouth_height)
            if ret_mouth_open is True:
                text = 'open mouth'
                cv2.putText(frame, "facial expression performed: " + text, (10, 50),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 255, 255), 1)
                cv2.putText(frame, "progress: " + str(progress_bool_open), (10, 20),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)

            if ret_mouth_close is True:
                text = 'close mouth'
                cv2.putText(frame, "facial expression performed: " + text, (10, 50),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 255, 255), 1)

                cv2.putText(frame, "progress: " + str(progress_bool_close), (10, 20),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 1)
            ret_smile, diff_smile = self.smile_and_pursing.calculating_smile(shape, faceAligned,
                                                                             lip_width_normal)
            ret_smile_image, diff_smile_image = self.smile_and_pursing.calculating_smile(shape, faceAligned,
                                                                                         lip_width_normal)
            progress_bool = self.smile_and_pursing.progress_smile(diff_smile, diff_smile_image)
            print("smile ", diff_smile)
            if ret_smile:
                cv2.putText(frame, "facial expression performed: " + "smile", (10, 80),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 255, 255), 1)
                cv2.putText(frame, "progress: " + str(progress_bool), (10, 20), cv2.FONT_HERSHEY_SIMPLEX,
                            0.7, (255, 255, 255), 1)
            ret_lip_pursing, diff_lip_pursing = self.smile_and_pursing.calculating_lip_pursing(shape,
                                                                                               faceAligned,
                                                                                               lip_width_normal)
            ret_lip_pursing_image, diff_lip_pursing_image = self.smile_and_pursing.calculating_lip_pursing(
                shape,
                faceAligned,
                lip_width_normal)
            progress_bool = self.smile_and_pursing.progress_lip_pursing(diff_lip_pursing,
                                                                        diff_lip_pursing_image)

            if ret_lip_pursing:
                cv2.putText(frame, "facial expression performed: " + "pucker lips", (10, 80),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 255, 255), 1)
                cv2.putText(frame, "progress: " + str(progress_bool), (10, 20), cv2.FONT_HERSHEY_SIMPLEX,
                            0.7, (255, 255, 255), 1)
        return frame

    def checkKey(self,dict, key):
        return key in dict.keys()

