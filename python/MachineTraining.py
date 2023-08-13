from keras.models import Sequential
from keras.layers import Dense,Flatten,Dropout
from keras.layers.convolutional import Conv2D,MaxPooling2D
from tensorflow.keras.layers import BatchNormalization
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
import pickle
from keras.utils import to_categorical

class MachineTraining:
    def __init__(self):
        self.data_train()

    def data_train(self):
        # Importing Data from CSV file
        data = pd.read_csv("reference_files/fer2013.csv")

        labels = data.iloc[:, [0]].values

        pixels = data['pixels']

        # Facial Expressions
        Expressions = {0: "Angry", 1: "Disgust", 2: "Fear", 3: "Happy", 4: "Sad", 5: "Surprise", 6: "Neutral"}
        labels = to_categorical(labels, len(Expressions))

        images = np.array([np.fromstring(pixel, dtype=int, sep=" ") for pixel in pixels])
        images = images / 255.0
        images = images.reshape(images.shape[0], 48, 48, 1).astype('float32')

        train_images, test_images, train_labels, test_labels = train_test_split(images, labels, test_size=0.2,
                                                                                random_state=0)

        classes = 7
        model = self.create_convolutional_model(classes)

        model.fit(train_images, train_labels, batch_size=105, epochs=30, verbose=2)

        label_pred = model.predict(test_images)
        label_pred = np.argmax(label_pred, axis=1)

        test_labels = np.argmax(test_labels, axis=1)
        # Compute confusion matrix
        class_names = Expressions

        # Save the classifier to a file
        with open('classifier.pkl', 'wb') as file:
            pickle.dump(model, file)

    def create_convolutional_model(self,classes):
        model = Sequential()
        model.add(Conv2D(32, kernel_size=(2, 2), strides=(1, 1), activation='relu', input_shape=(48, 48, 1)))
        model.add(BatchNormalization())
        model.add(MaxPooling2D(pool_size=(2, 2), strides=(2, 2)))
        model.add(Dropout(0.25))

        model.add(Conv2D(filters=64, kernel_size=(2, 2), strides=(1, 1), activation='relu'))
        model.add(BatchNormalization())
        model.add(MaxPooling2D(pool_size=(2, 2), strides=(1, 1)))
        model.add(Dropout(0.25))  # to prevent neural network from overfitting

        model.add(Conv2D(filters=128, kernel_size=(2, 2), strides=(1, 1), activation='relu'))
        model.add(BatchNormalization())
        model.add(MaxPooling2D(pool_size=(2, 2), strides=(1, 1)))
        model.add(Dropout(0.25))

        model.add(Conv2D(filters=256, kernel_size=(2, 2), strides=(1, 1), activation='relu'))
        model.add(BatchNormalization())
        model.add(MaxPooling2D(pool_size=(2, 2), strides=(1, 1)))
        model.add(Dropout(0.25))

        model.add(Flatten())

        model.add(Dense(256, activation='relu'))
        model.add(BatchNormalization())
        model.add(Dropout(0.25))

        model.add(Dense(512, activation='relu'))
        model.add(BatchNormalization())
        model.add(Dropout(0.25))

        model.add(Dense(classes, activation='softmax'))

        model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

        return model

