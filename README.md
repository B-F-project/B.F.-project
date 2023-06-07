# B.F.-project
Gachon-University Graduation Project(22-23)

## Contributor
202035301 Seongyeon Kang
<br>
202035353 Soyeon You
<br>
201934240 Mina Lee
<br>
202035346 Eunji Shin

## Watch demo Video
[Introduction Video(22-1)](https://www.youtube.com/watch?v=wDGVZBWaSlU)

[Breif Description Video(22-2)](https://youtu.be/dgLG6T18XZI)

https://www.youtube.com/watch?v=dgLG6T18XZI&feature=youtu.be

## Introduction
Barrier free application for deaf is an Android application developed in Kotlin that aims to provide a user-friendly and accessible platform for individuals with hearing impairments. The application utilizes various technologies such as **Speech-to-Text (STT)** and **Natural Language Processing (NLP)** to enhance communication and improve accessibility for deaf users.

## Getting Started
To use the barrier-free application for the hearing impaired, download and install it on your Android device through Android Studio. Once installed, launch the application and follow the on-screen instructions to use it, referring to the guide videos as needed.

## Directory
```
B.F.-project/
.
├── app
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       ├── main
│       │   ├── assets
│       │   ├── java
│       │   │   └── com
│       │   │       └── example
│       │   │           └── bfproject
│       │   │               ├── activities
│       │   │               ├── adapters
│       │   │               ├── models
│       │   │               └── utilities
│       │   └── res
│       │       ├── drawable
│       │       ├── layout
│       │       ├── mipmap-anydpi-v26
│       │       ├── mipmap-hdpi
│       │       ├── mipmap-mdpi
│       │       ├── mipmap-xhdpi
│       │       ├── mipmap-xxhdpi
│       │       └── values
│       └── test
│           └── java
└── build.gradle
```

## DataBase and DataSource
DataBase: Real-time Firebase
A barrier-free application for the deaf utilizes a real-time Firebase database to store and retrieve sign language video data.
<br>
<br>
DataSource: [AI hub](https://www.aihub.or.kr/aihubdata/data/view.do?currMenu=115&topMenu=100&aihubDataSe=realm&dataSetSn=103)
<br>
<br>
Backend(using Firebase): 
The backend architecture keeps video data seamlessly in Firebase storage to increase response speed and quality.

## Model Documentation
Barrier-free applications for the hearing impaired incorporate the following models

Speech-to-text (STT): The application uses the Google Speech-to-Text (STT) model to convert spoken language to written text. Google STT uses advanced machine learning algorithms to accurately convert speech to text in real time.

Natural language processing (NLP): The application uses Kkma morphological tokenization and lematization models for natural language processing. These models analyze transcribed text and break it down into its component parts, such as words and grammatical structures. This analysis allows the application to improve the accuracy of the video output.

## Flow Chart
* **Technical Flow**
<br>
<img width="700" alt="image" src="https://github.com/B-F-project/B.F.-project/assets/71324520/79902d6e-1d44-4ce2-915e-d49854d5fbfd">


* **Data Preprocessing**
<img width="1000" alt="image" src="https://github.com/B-F-project/B.F.-project/assets/71324520/2a97b1c3-cffc-409e-ac24-358909c0403b">


* **NLP/Lemmatization Flow**
<img width="700" alt="image" src="https://github.com/B-F-project/B.F.-project/assets/71324520/749decc6-70d5-4723-abd7-426419e7d620">


* **Cost Function**
<img width="1000" alt="image" src="https://github.com/B-F-project/B.F.-project/assets/71324520/1a246818-c629-4399-aaa9-a231d28e3bd4">

## UI/UX & Features
<img width="979" alt="스크린샷 2023-06-05 오후 8 38 49" src="https://github.com/B-F-project/B.F.-project/assets/71324520/5580ba39-deb9-4c32-a7f1-812646d9260c">

<img width="979" alt="스크린샷 2023-06-05 오후 8 39 02" src="https://github.com/B-F-project/B.F.-project/assets/71324520/74db0878-642a-40e5-aa35-03fcfa279375">

<img width="979" alt="스크린샷 2023-06-05 오후 8 39 12" src="https://github.com/B-F-project/B.F.-project/assets/71324520/98309a2a-34f8-4ae4-ae7f-c32672403a98">

<img width="979" alt="스크린샷 2023-06-07 오후 2 20 42" src="https://github.com/B-F-project/B.F.-project/assets/71324520/c4abefc3-7d6b-486e-9194-fab249da3903">

## Resources
[Kkma library] http://kkma.snu.ac.kr/documents/
<br>
[Google STT API] https://cloud.google.com/speech-to-text?utm_source=google&utm_medium=cpc&utm_campaign=japac-KR-all-en-dr-BKWS-all-hv-trial-PHR-dr-1605216&utm_content=text-ad-none-none-DEV_c-CRE_631194514014-ADGP_Hybrid%20%7C%20BKWS%20-%20BRO%20%7C%20Txt%20~%20AI%20&%20ML_Speech-to-Text_google%20speech_get-KWID_43700076521336898-kwd-1148277301165&userloc_1009875-network_g&utm_term=KW_get%20google%20speech&gclid=Cj0KCQjw7PCjBhDwARIsANo7CgkQ6CkGRsknvIYFqTCElMT9E11fRm0UJR2WXa6TYaU1dQSVsjwPHn8aAitJEALw_wcB&gclsrc=aw.ds&hl=ko
