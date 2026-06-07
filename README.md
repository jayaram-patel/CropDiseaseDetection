# Crop Disease Detection using an IoT device

## What is does -
An image of a leaf is captured using the PICamera of a Raspberry Pi. This image is then run through a classifier and it's disease is identified. This data is sent to your android device using a cloud service (ThingSpeak). The Android app displays the leaf, it's diagnosed disease, the preventive measure needed to be taken up by the farmer and the cure for this disease. Command to click the image can be sent to the RasPi using the Android app.

## How it works -

1. Training the classifier

The plant disease classifier was built and trained on a GPU. The dataset used was Plant Village dataset which can be found here - https://www.kaggle.com/emmarex/plantdisease. For the purpose of this project, 3 plants were chosen from the complete dataset - Tomato, Bell pepper and Potato. In all, there are 14 classes of diseases and healthy leaves. After training the classifier, the model is saved in a Hierarchical Data Format (h5 extension)

2. Deploying the classifier

Next, this model was deployed on a Raspberry Pi device using Tensorflow Lite. The module captures an image using the PICamera and sends this image to the classifier. This classifier then predicts the class of the image and sends the image as well as it's class to the Android app using a ThingSpeak channel. 

3. Obtaining the results

On the Android app, the user can see the image, it's predicted class and related information about the disease. This app is connected to the ThingSpeak channel using an HTTP and MQTT protocol. It follows a publish-subscribe model. The app can also send a command to the IoT device to click images. This connection is a TCP connection.
