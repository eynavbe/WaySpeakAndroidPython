# WaySpeak
## Table of Contents  
 - [Introduction](#introduction)  
   - [Purpose](#purpose)  
 
### Purpose
An application that will help people suffering from neurological problems in the rehabilitation stages, by analyzing their face.
. The application is intended for speech therapists and their patients.

## Introduction
People who suffer from various neurological impairments have difficulty speaking and expressing facial expressions, so there is a need for an application that will help them practice the difficulty outside of meetings with the communication therapist accompanying the process.
An application for practicing ORAL MOTOR.
the steering), with the aim of helping to rehabilitate people who have undergone a neurological injury. The rehabilitation is carried out by
Practicing the facial expressions, people whose ability to speak was damaged by the injury
Neurologically they will be able to restore their steering for speaking.
###### The goals of the app from the clinician's side:
1) Possibility to assign exercises to patients related to facial expressions in the mouth
which enable clear and intelligible speech.
2) Monitoring and analyzing the patient's progress in performing exercises with data that will be displayed
to the clinician according to the results of the patient's practice.
3) Entering progress and feedback to the patient.

###### The goals of the application from the patient's side:
1) Possibility to practice the exercises assigned to him at home outside of visiting hours
the clinician
2) Receiving feedback for performing the exercise. Receiving the feedback will be done after each exercise but
There will also be a general presentation of the progress.

</br>
The goals from a technical point of view are for the application to function fully and for it to recognize
In a good and accurate way the facial expressions of the patient and analyze them.
The goal of the project in general is that the application can be used by the hospital
Levinstein who specializes in rehabilitation and the Department of Communication Disorders (the idea came from them).
The advantage of the app is providing a platform for practicing facial expressions outside of hours
The meeting with a clinician independently and the possibility of the clinician following me
The remote progress. Such an application is currently missing and that is why we received an application
From the Department of Communication Disorders and the Levinstein Hospital to develop such an application.


## Development
In order to help with the training of the facial expressions, it was necessary to analyze the facial expressions using different methods. Some of the expressions were analyzed using machine learning methods, some with calculation inventions according to landmarks and some using different software such as Matlab. Linking the various algorithms to the application was performed by an external server.</br>
The application will upload a video to the data and link through the server to a Python code that will analyze the video and update the data if the patient performed successfully, if the patient made progress. And a picture of one frame from the video where the patient made the best expression will come up. The application will pull the updated information from the data and present it to the communication clinician.
### Application
#### Clinician Side:



https://github.com/eynavbe/WaySpeak/assets/93534494/66c34ecd-d60f-472d-962f-19c4ae79a748




#### Patient Side:
https://github.com/eynavbe/WaySpeak/assets/93534494/6fdc7a41-b93a-4843-aa68-4f64ab0b3014

### Facial Expression Analysis
The patient will take a video of him performing the expression requested of him via the app. The video will be sent for analysis which will determine:
- If the patient performed the expression successfully
- If the patient has made progress from his best performance.</br>


The image of the best expressive performance in the video will be saved and shown to the speech therapist in the application or for the purpose of comparing progress.</br></br>
The facial expressions analyzed:
1) Mouth open
2) Mouth closed
3) Smile
4) Puckering of lips
5) Stick out the tongue in a straight line
6) Move the tongue to the right
7) Move the tongue to the left
8) Lift the tongue towards the nose
9) Lower the tongue towards the chin
</br>


https://github.com/eynavbe/WaySpeak/assets/93534494/4cb2c13a-4693-40dc-99ad-0fa957f12670



#### Result
Facial expression analysis was tested on 8 people, 3 men and 5 women.
##### 1) Mouth open
Precision: 0.87 </br>
Recall: 1.0 </br>
Accuracy: 0.93 </br>
F1-score: 0.93 </br>

##### 2) Mouth closed

##### 3) Smile
Precision: 0.74 </br>
Recall: 0.94 </br>
Accuracy: 0.80 </br>
F1-score: 0.83 </br>
##### 4) Puckering of lips
##### 5) Stick out the tongue in a straight line
##### 6) Move the tongue to the right
##### 7) Move the tongue to the left
##### 8) Lift the tongue towards the nose
##### 9) Lower the tongue towards the chin
### Server




