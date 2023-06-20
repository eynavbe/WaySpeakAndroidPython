from imutils.video import VideoStream
import cv2
from AnalysisFrame import AnalysisFrame


class AnalysisRealTime:
    def __init__(self):
        self.vs = VideoStream(0).start()
        self.analysis_frame = AnalysisFrame()
        self.lip_width = self.analysis_frame.lip_width_image_get('eynav.png')
        self.analysis_every_frame()
        cv2.destroyAllWindows()
        self.vs.stop()

    def analysis_every_frame(self):
        while True:
            frame1 = self.vs.read()
            shape, frame, faceAligned = self.analysis_frame.analysis_one_frame(frame1)
            frame = self.analysis_frame.analysis_facial_expressions(self.lip_width,shape, frame, faceAligned)
            cv2.imshow("output", frame)
            key = cv2.waitKey(1) & 0xFF
            if key == ord("q"):
                break