# BatiKita-Capstone

Batik is a traditional motif cloth originating from Indonesia. Batik is already well known around the world and batik also contributes big numbers to Indonesia’s economy, specifically in export. However, because batik has a lot of motifs, most people still do not know the meaning or the background story of each batik’s motif. Our team came up with an idea for an app that can recognize batik’s motif based on batik’s motif that has already spread around the market all over the world and provides the meaning and the background story of the Batik.

We plan to use human-centered design techniques to develop an application that can help users to gain a better understanding of batik. Our research questions include: How can we recognize batik’s motif correctly? How can we provide similar batik’s motif? 

We believe that batik is one of Indonesia's most important heritage cultures that needs to be preserved. We are sure that we can contribute to preserving this culture by sharing knowledge about batik to others. We also see the potential of this application used by the government to help them introduce batik more to the world. 

Machine learning: Using the Indonesian Batik Motifs (Corak App) dataset from Kaggle with 10 classes of batik motifs (we used the smaller class dataset included) and a total of 900 images. Preprocessing the training images using ImageDataGenerator to augment them and make the size uniform. Build an image classification model using transfer learning (using MobileNetV2 as the pre-trained model) and then train the data. Then convert the model to HDF5 (.h5) and TFLite format.
Cloud computing: We are using Google Cloud Platform as our main platform to develop the project. For Machine Learning and Backend deployment, we are using Cloud Run. We store our static assets in Cloud Storage and we are also using Cloud Build for our CI/CD Pipelines. For the Backend API, we are using Fast API to develop because it is easier to wire the backend and the frontend if we are using Fast API.
Mobile development: 
Creating user flow and mockups design in Figma and implementing user interface design into XML layout. Implementing the trained model for image recognition created by Machine Learning in .tflite model into application. Using REST API to get batik information from Backend server.
