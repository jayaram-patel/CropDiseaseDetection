<p align="center">
  <img src="img1.JPG" alt="Crop Disease Detection" width="200"/>
</p>

<h1 align="center">Crop Disease Detection using IoT</h1>

<p align="center">
  <strong>An end-to-end IoT pipeline for real-time crop disease identification using Deep Learning, Raspberry Pi, and Android</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Python-3.5+-3776AB?style=for-the-badge&logo=python&logoColor=white" alt="Python"/>
  <img src="https://img.shields.io/badge/TensorFlow-1.x-FF6F00?style=for-the-badge&logo=tensorflow&logoColor=white" alt="TensorFlow"/>
  <img src="https://img.shields.io/badge/Keras-Sequential-D00000?style=for-the-badge&logo=keras&logoColor=white" alt="Keras"/>
  <img src="https://img.shields.io/badge/Raspberry_Pi-IoT-C51A4A?style=for-the-badge&logo=raspberrypi&logoColor=white" alt="Raspberry Pi"/>
  <img src="https://img.shields.io/badge/Android-App-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/ThingSpeak-Cloud-0078D4?style=for-the-badge&logo=mathworks&logoColor=white" alt="ThingSpeak"/>
</p>

---

## Overview

This project implements a complete **smart agriculture** solution that captures images of plant leaves using a Raspberry Pi equipped with a PiCamera, classifies the leaf disease using a Convolutional Neural Network (CNN), and relays results to an Android application through the **ThingSpeak** cloud platform.

The system enables farmers to quickly identify crop diseases in real-time, receive actionable preventive measures, and learn about suitable cures — all from their mobile device.

---

## Key Features

- **Deep Learning Classifier** — Custom CNN trained on the [PlantVillage dataset](https://www.kaggle.com/emmarex/plantdisease) to identify **15 disease classes** across 3 crop types
- **Multi-Crop Support** — Detects diseases in **Tomato**, **Bell Pepper**, and **Potato** plants
- **IoT Image Capture** — Uses a Raspberry Pi with PiCamera for automated leaf image capture
- **Cloud Integration** — Leverages ThingSpeak (HTTP & MQTT) for seamless IoT-to-mobile communication
- **Android Companion App** — Displays the captured leaf image, diagnosed disease, preventive measures, and cures
- **Remote Trigger** — Send capture commands from the Android app to the Raspberry Pi over TCP

---

## System Architecture

```
┌─────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│                 │       │                  │       │                  │
│  Raspberry Pi   │──────▶│   ThingSpeak     │──────▶│   Android App    │
│  + PiCamera     │ HTTP/ │   Cloud Service  │ MQTT/ │   (Crop_Disease) │
│  + TF Lite      │ MQTT  │                  │ HTTP  │                  │
│                 │       │                  │       │                  │
└─────────────────┘       └──────────────────┘       └──────────────────┘
        ▲                                                    │
        │                   TCP Connection                   │
        └────────────────────────────────────────────────────┘
                        (Capture Command)
```

---

## Model Architecture

The classifier uses a custom **Sequential CNN** built with Keras, designed for efficient inference on edge devices:

| Layer | Type | Output Shape | Parameters |
|-------|------|-------------|------------|
| 1 | Conv2D (32 filters, 3×3) | 64 × 64 × 32 | 896 |
| 2 | BatchNorm + ReLU + MaxPool(3×3) + Dropout(0.25) | 21 × 21 × 32 | 128 |
| 3–4 | Conv2D (64 filters, 3×3) × 2 | 21 × 21 × 64 | 55,680 |
| 5 | MaxPool(2×2) + BatchNorm + Dropout(0.25) | 10 × 10 × 64 | 256 |
| 6–7 | Conv2D (128 filters, 3×3) × 2 | 10 × 10 × 128 | 221,952 |
| 8 | MaxPool(2×2) + BatchNorm + Dropout(0.25) | 5 × 5 × 128 | 512 |
| 9 | Flatten → Dense(256) + BN + ReLU + Dropout(0.4) | 256 | 820,480 |
| 10 | Dense(512) + BN + ReLU + Dropout(0.4) | 512 | 133,632 |
| 11 | Dense(15) + Softmax | 15 | 7,695 |

> **Total Parameters:** ~1.24M (1,236,682 trainable)

### Training Configuration

| Parameter | Value |
|-----------|-------|
| **Optimizer** | Adam (lr = 0.001) |
| **Loss Function** | Categorical Cross-Entropy |
| **Batch Size** | 32 |
| **Epochs** | 30 |
| **Train/Test Split** | 80/20 |
| **Input Size** | 64 × 64 × 3 |
| **Data Augmentation** | Rotation (±5°), Width/Height shift (0.1), Horizontal flip |

---

## Supported Disease Classes

The model can identify the following **15 classes**:

| # | Crop | Disease |
|---|------|---------|
| 1 | 🫑 Bell Pepper | Bacterial Spot |
| 2 | 🫑 Bell Pepper | Healthy |
| 3 | 🥔 Potato | Early Blight |
| 4 | 🥔 Potato | Late Blight |
| 5 | 🥔 Potato | Healthy |
| 6 | 🍅 Tomato | Bacterial Spot |
| 7 | 🍅 Tomato | Early Blight |
| 8 | 🍅 Tomato | Late Blight |
| 9 | 🍅 Tomato | Leaf Mold |
| 10 | 🍅 Tomato | Septoria Leaf Spot |
| 11 | 🍅 Tomato | Spider Mites (Two-spotted) |
| 12 | 🍅 Tomato | Target Spot |
| 13 | 🍅 Tomato | Yellow Leaf Curl Virus |
| 14 | 🍅 Tomato | Mosaic Virus |
| 15 | 🍅 Tomato | Healthy |

---

## Project Structure

```
CropDiseaseDetection/
├── Plant_disease.ipynb     # Jupyter notebook — model training pipeline
├── code.py                 # Standalone inference script
├── my_model.h5             # Pre-trained Keras model (HDF5 format)
├── img1.JPG                # Sample leaf image for testing
├── img2.JPG                # Sample leaf image for testing
├── img3.JPG                # Sample leaf image for testing
├── README.md               # This file
│
└── Crop_Disease/            # Android companion app
    ├── build.gradle            # Root Gradle build config
    ├── settings.gradle
    ├── gradlew / gradlew.bat   # Gradle wrapper scripts
    └── app/
        ├── build.gradle        # App-level Gradle config
        └── src/main/
            ├── AndroidManifest.xml
            ├── java/com/example/user/crop_disease/
            │   ├── MainActivity.java
            │   ├── FetchThingspeakTask.java   # Fetches data from ThingSpeak
            │   └── SendThingspeakTask.java    # Sends commands to ThingSpeak
            └── res/                           # Android resources (layouts, drawables, etc.)
```

---

## Getting Started

### Prerequisites

- **Python 3.5+**
- **TensorFlow 1.x** and **Keras**
- **OpenCV** (`cv2`)
- **NumPy**, **scikit-learn**, **Matplotlib**, **Pillow**
- **Raspberry Pi** with PiCamera (for deployment)
- **Android Studio** (for building the companion app)
- A **ThingSpeak** account and channel

### 1. Clone the Repository

```bash
git clone https://github.com/jayaram-patel/CropDiseaseDetection.git
cd CropDiseaseDetection
```

### 2. Install Python Dependencies

```bash
pip install tensorflow keras opencv-python numpy scikit-learn matplotlib Pillow
```

### 3. Train the Model (Optional)

A pre-trained model (`my_model.h5`) is included. To retrain:

1. Download the [PlantVillage dataset](https://www.kaggle.com/emmarex/plantdisease) from Kaggle
2. Extract Tomato, Bell Pepper, and Potato folders
3. Update the `directory_root` path in `Plant_disease.ipynb`
4. Run the notebook end-to-end on a GPU-enabled machine

### 4. Run Inference Locally

```bash
python code.py
```

> **Note:** Update the image path and model filename in `code.py` as needed.

### 5. Build the Android App

1. Open the `Crop_Disease/` directory in **Android Studio**
2. Sync Gradle dependencies
3. Configure your ThingSpeak channel keys in the source code
4. Build and deploy to an Android device

### 6. Deploy on Raspberry Pi

1. Transfer the trained model (`.h5`) to the Raspberry Pi
2. Convert to **TensorFlow Lite** format for optimized edge inference
3. Set up the PiCamera capture script
4. Configure ThingSpeak channel credentials for data transmission

---

## 🔧 How It Works

### Step 1 — Training the Classifier
The CNN is trained on a GPU using the **PlantVillage dataset** (~18,000 images across 15 classes). The trained model is exported in HDF5 format (`.h5`).

### Step 2 — Edge Deployment
The model is deployed on a **Raspberry Pi** using **TensorFlow Lite**. The device captures leaf images via PiCamera, runs inference, and uploads both the image and prediction to a **ThingSpeak channel** using HTTP/MQTT.

### Step 3 — Mobile Visualization
The **Android app** subscribes to the ThingSpeak channel using a **publish-subscribe model** (MQTT). It displays:
- The captured leaf image
- The predicted disease class
- Preventive measures
- Cure recommendations

The app can also send a **TCP command** back to the Raspberry Pi to trigger a new image capture.

---

## Dataset

- **Source:** [PlantVillage Dataset on Kaggle](https://www.kaggle.com/emmarex/plantdisease)
- **Subset Used:** Tomato, Bell Pepper, Potato
- **Total Images:** ~18,000
- **Train Set:** 14,527 images
- **Test Set:** 3,632 images
- **Image Dimensions:** 64 × 64 × 3 (RGB)

---

## Tech Stack

| Component | Technology |
|-----------|-----------|
| **ML Framework** | TensorFlow 1.x + Keras |
| **Model Format** | HDF5 (`.h5`) / TensorFlow Lite |
| **Edge Device** | Raspberry Pi + PiCamera |
| **Cloud Platform** | ThingSpeak (MathWorks) |
| **Communication** | HTTP, MQTT, TCP |
| **Mobile App** | Android (Java) |
| **Key Libraries** | OkHttp3, AppCompat |
| **Training Env** | Jupyter Notebook + GPU |

---

## Dependencies

### Python
```
tensorflow==1.x
keras
opencv-python
numpy
scikit-learn
matplotlib
Pillow
```

### Android (Gradle)
```groovy
implementation 'com.android.support:appcompat-v7:28.0.0'
implementation 'com.android.support.constraint:constraint-layout:1.1.3'
implementation 'com.squareup.okhttp3:okhttp:3.9.1'
```

---

## Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---
