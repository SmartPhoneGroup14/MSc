MSc App

# Background
Our MSc(CS) programme office has launched a homepage (https://www.msc-cs.hku.hk/) for several years to promote the programme and to attract potential students over the world. The site already has a mobile version but it is not tailor-made to the mobile environment. This is a smart phone application for our MSc(CS) programme.

# Main Function
You can get details in the description.docx.

# Compile
This app's compileSdkVersion is API 28 while the minSdkVersion is API 21.
You can import the project and click 'Make Project' with Android Studio.
After downloading some required sdks, the project will build successfully.

# Execute
Create a virtual device and run 'app' on it. 
We recommend the hardware 'Nexus 5X' 1080 * 1920 because the app can get the best visual effect on this kind device.
Internet is necessary when using the app otherwise you can not get the newest news & events.


# Generate apk
If you want to install the app on your andorid devices, you need to generate the suitable apk.
Just click Build > Generate Signed APK > app > Create new (sign key) > Next ... > Finish > Reveal the apk in Finder.

# Dependencies
implementation 'com.jakewharton:butterknife:8.8.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
implementation 'com.bigkoo:convenientbanner:2.0.5'
implementation 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar'
implementation 'org.jsoup:jsoup:1.10.2'