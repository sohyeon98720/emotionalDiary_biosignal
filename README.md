# 심장으로 쓰는 감성일기(안드로이드+PPG센서)

### 교내 COC참가(2018.08.27~2018.08.31)

----------

__주제__: 심장으로 쓰는 감성일기

__제안배경__: 기존의 일기는 번거롭고 자기 전에 쓰려고하면 막상 생각이 안나는 경우가 종종있음                   
              *-> 바쁜 일상 속 **쉽고 간편하게 하루를 되돌아 볼 수 있는 일기장**을 고안하게됨*
          
------------------

__프로젝트 내용__:                          
__*참고) PPG센서 없이는 어플 사용이 불가함*__

__1. 하드웨어:__                                                     
<img src="https://user-images.githubusercontent.com/47767202/78423848-31c61280-76a4-11ea-8301-6fe68343bd7a.png" width="40%">     

> PPG센서 - 빛을 조사한 혈관에 투과된 광량을 측정. 착용이 비교적 간편함.                                   

----------------
     
__2. 활용이론:__ Russell(러셀)의 감정차원모델                                
<img src="https://user-images.githubusercontent.com/47767202/78625822-028feb00-78c8-11ea-8f6e-aca6ffd0d4b1.png" width="70%">

> 각성(Arousal), 정서(Valence)의 2개의 축을 이용하여 감정을 나타냄

<br>

<img src="https://user-images.githubusercontent.com/47767202/78868961-c488f780-7a7e-11ea-9a04-04d34ad02077.PNG" width="40%">

> 최대 진폭과 PPI에 따른 감정 어휘 정의
>> 출처: 이현우, et al. Lifelogging 을 활용한 Customer Journey Map 시스템. 한국 HCI 학회 학술대회, 2016, 266-272.


--------------


__3. 시스템 처리과정:__          
- 전체                 
<img src="https://user-images.githubusercontent.com/47767202/78869962-56453480-7a80-11ea-8cde-888411133232.PNG" width="60%">        

<br>

- 자동화 부분 데이터 플랫폼 개념도        

<img src="https://user-images.githubusercontent.com/47767202/79059960-d951cf00-7cba-11ea-97ae-5e2b54ad5a3b.PNG" width="70%">   

<br>

- 수동 부분 데이터 플랫폼 개념도           

<img src="https://user-images.githubusercontent.com/47767202/78870091-8a205a00-7a80-11ea-8321-97a3ba810b90.PNG" width="70%">

- 감성 추출 부분
<img src="https://user-images.githubusercontent.com/47767202/80185706-bf63a500-8647-11ea-96a1-c721d10b09d0.png" width="30%">


-------------

__4. 사용 프로그램:__

- Andrid Studio: 어플 제작
  - 사진과 함께 그 순간의 감성을 저장하는 일기가 결과물이기 때문에, 카메라의 접근성이 좋은 핸드폰에서 쉽게 사용할 수 있도록 어플리케이션을 선택하였다.

-------------

__5. 결과:__

- 앱아이콘                                                             
  <img src="https://user-images.githubusercontent.com/47767202/79531199-94f47380-80ac-11ea-8c77-a5fe6390c939.png" width="20%">
  
- 메인화면: 사진첩(diary)로 이동하는 버튼, PPG센서와 연결 및 PPG 데이터 표시부분                                                             
  <img src="https://user-images.githubusercontent.com/47767202/79531635-f23cf480-80ad-11ea-99f9-b10a018be3b5.JPG" width="20%">
  
- 특별한 감성이 포착되었을때 or 수동으로 카메라 기능을 이용하고싶을때:              
  <img src="https://user-images.githubusercontent.com/47767202/79531459-5f9c5580-80ad-11ea-943b-7c5425e6a052.JPG" width="20%">
  
- 사진첩(diary)로 이동하는 버튼을 눌렀을때 > 날짜형 선택:                               
  <img src="https://user-images.githubusercontent.com/47767202/79881610-63710480-842c-11ea-87f9-c8bc82863615.JPG" width="20%">

- 사진첩(diary)로 이동하는 버튼을 눌렀을때 > 감성형 선택:                                                
  <img src="https://user-images.githubusercontent.com/47767202/79881770-9ddaa180-842c-11ea-9c1c-b8b694eabf49.JPG" width="20%">
