from AnalysisRealTime import AnalysisRealTime
# import Tests
# import unittest
# from MachineTraining import MachineTraining
from ConnectFirebase import ConnectFirebase
from Tongue import Tongue

if __name__ == "__main__":
    # AnalysisRealTime('eynav.png', 'eynav.png', "mouth_open")
    ConnectFirebase("39320201009","mouth_open", "04062023", "mouth_open5","1959_310723 ")

    Tongue('straight_tongue_out.mp4','Move_tongue_to_left')
    # MachineTraining()
    # unittest.main(module=Tests)
