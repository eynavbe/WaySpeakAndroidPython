# WaySpeak
## Table of Contents  
 - [Purpose](#purpose)  
 - [Introduction](#introduction)  
 - [System Architecture](#system-architecture)  
 - [Development](#development) 
   - [Application](#application) 
      - [Clinician Side](#clinician-side) 
      - [Patient Side](#patient-side)
   - [Facial Expression Analysis](#facial-expression-analysis) 
      - [The classification of each facial expression](#the-classification-of-each-facial-expression)
      - [Result](#result)
   - [Database](#database)
   - [Server](#server) 
 - [Summary](#summary) 

## Purpose
An application that will help people suffering from neurological problems in the rehabilitation stages, by analyzing their face.
. The application is intended for speech therapists and their patients.

## Introduction
People who suffer from various neurological impairments have difficulty speaking and expressing facial expressions, so there is a need for an application that will help them practice the difficulty outside of meetings with the communication therapist accompanying the process.
An application for practicing ORAL MOTOR.
the steering), with the aim of helping to rehabilitate people who have undergone a neurological injury. The rehabilitation is carried out by
Practicing the facial expressions, people whose ability to speak was damaged by the injury
Neurologically they will be able to restore their steering for speaking.
###### Goals of clinician side
1) Possibility to assign exercises to patients related to facial expressions in the mouth
which enable clear and intelligible speech.
2) Monitoring and analyzing the patient's progress in performing exercises with data that will be displayed
to the clinician according to the results of the patient's practice.
3) Entering progress and feedback to the patient.

###### Goals of patient side
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

## System Architecture
<img width="1291" alt="system_architecture" src="https://github.com/eynavbe/WaySpeak/assets/93534494/9976aa13-b017-4c31-9be4-4e4b35f6d9eb">


## Development
In order to help with the training of the facial expressions, it was necessary to analyze the facial expressions using different methods. Some of the expressions were analyzed using machine learning methods, some with calculation inventions according to landmarks and some using different software such as Matlab. Linking the various algorithms to the application was performed by an external server.</br>
The application will upload a video to the data and link through the server to a Python code that will analyze the video and update the data if the patient performed successfully, if the patient made progress. And a picture of one frame from the video where the patient made the best expression will come up. The application will pull the updated information from the data and present it to the communication clinician.
### Application
#### Clinician Side



https://github.com/eynavbe/WaySpeak/assets/93534494/66c34ecd-d60f-472d-962f-19c4ae79a748




#### Patient Side
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

#### The classification of each facial expression
##### Landmarks of the face
Landmarks of the face refer to specific points on a person's face that are used to identify and analyze facial features. </br>

 Landmarks of the face  of 64 predefined points on a face. These points are positioned at key locations such as the corners of the eyes, nose, mouth, and other significant facial structures. </br>

Facial landmark detection algorithms typically rely on machine learning techniques, including deep learning and computer vision methods. These algorithms are trained on large datasets of labeled facial images, where the landmarks' positions are manually annotated. The trained models can then predict the facial landmarks' coordinates in real-time on new images or video frames.</br>

The libraries to facial landmark detection as dlib, OpenCV. and The file pre-trained model to facial landmark "shape_predictor_68_face_landmarks.dat".</br>

##### Alignment, centering and approximation of the face
The face is rotated so that the eyes are along the same y coordinates.</br>
The face size will be roughly the same.</br>
The algorithm is based on Chapter 8 of Mastering OpenCV with Practical Computer Vision Projects.</br>
After receiving the points of the eyes, left eye and right eye. Calculate the center of each eye by averaging all the points in each eye. and calculating the differences between the center points of each eye get the angle between the centers of the eye.
With the help of the angle it will be possible to rotate the image.
to determine the angle of rotation needed to align the eyes on the same y-axis. This can be done by calculating the angle between the line connecting the center points of the eyes and the horizontal axis.
Obtaining a transformation matrix that describes the rotation operations by using the angle of rotation, translation and scaling applied to an image. Using the rotation matrix to flip the image.

##### learning machine for detecting mouth opening and smiling
The Deep Learning model is trainind on the around 29,000 imges of persons with different facial expressions. </br>
dataset of kaggle - FER-2013. [https://www.kaggle.com/datasets/msambare/fer2013](https://www.kaggle.com/datasets/deadskull7/fer2013?select=fer2013.csv)

##### Matlabfor Identification Move Tongue

###### 1) Mouth open
The aligned image is sent to the learning machine trained to recognize an open mouth, if after machine learning it is accepted that the mouth is closed then the image will be sent to calculations, Landmarks of the mouth were obtained from the image, which with the help of the points is calculated:
  - the height of the lower lips - by calculating the average distance of the points of the upper lips
- The height of the upper lips - by calculating the average distance of the points of the upper lips
- The height of the distance between the upper and lower lips - by calculating the average of the distances of the lower points of the upper lip between the upper points of the lower lip.
- The ratio - the minimum size of the height of the lower lips or the height of the upper lips.


If the height of the space between the upper and lower lips is greater than the ratio between the height of the lower lips and the height of the upper lips, then the mouth is open.
###### 2) Mouth closed
If after the algorithm that is found for testing the opening of the mouth, False comes out, then the mouth is closed.
###### 3) Smile
The aligned image is sent to the learning machine trained to recognize an smile, if after machine learning it is accepted that no smile then the image will be sent to calculations, Landmarks of the mouth were obtained from the image, which with the help of the points is calculated:
###### 4) Puckering of lips
###### 5) Stick out the tongue in a straight line
###### 6) Move the tongue to the right
###### 7) Move the tongue to the left
###### 8) Lift the tongue towards the nose
###### 9) Lower the tongue towards the chin
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

### Database
- Android studio is linked to firebase.
- The Python code is linked to Firebase.
- registers and reads the information there.
  
### Server
Connect between the Android application written in Java and facial expression analysis written in Python.
To connect an Android application to python, create a Flask server deployed on Glitch,  use HTTP requests to communicate between the two. The Android application make HTTP requests to specific endpoints on the Flask server, and the server will have the Python code for the video and will update the database. The app will connect to a database and pull the data from there.



