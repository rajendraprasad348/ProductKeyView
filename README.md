# ProductKeyView
This library helps to enter  dynamic number of digits ( product key)  in editable fields and also user can change their edit fields as customizable like background change, no. of digits enter, text size, view enabled/disabled etc.

# Version
 [![](https://jitpack.io/v/rajendraprasad348/ProductKeyView.svg)](https://jitpack.io/#rajendraprasad348/ProductKeyView)
 
# Description

 Please refer Example application shown in app section

This library can be used to enter ' n ' multiples of digit in a edit box and also user can be directly paste the keys like product key / activation key /credit card number etc. in a  customized edit box.

 # Demo
 https://youtu.be/MqkRd-9W_tw

# Installation

      Step1:  Add it in your root build.gradle at the end of repositories:
           allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
	               }
	            }  
	
	  Step2:  Add the dependency
         dependencies {
	       	      	     implementation 'com.github.rajendraprasad348:ProductKeyView:1.0'
	                 } 
  
 
 # Download APK
   https://www.dropbox.com/s/fcjbwoctxqul9ku/ProductKeyApp.apk?dl=0 
   
  # Usage
  
  Usage in class file :
  
        pkv.setPkvItemHeight(6);
        pkv.setPkvItemBackground(getResources().getDrawable(R.drawable.rectangle_box));
        pkv.setPkvMaxLength(4);
        pkv.setPkvEnabled(true);
        pkv.setPkvHint("- - - -");
        pkv.setPkvHintColor(getResources().getColor(R.color.text_default_color));
        pkv.setPkvTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pkv.setPkvTextSize(50.00f);
        pkv.setPkvTextStyle(3);
        pkv.setPkvViewBackground(getResources().getColor(R.color.pkv_default_color));
        
        
        
   OR 
        
   Usage in XML file :
   
    <com.rajendra_prasad.productkeylibrary.ProductKeyView
        android:id="@+id/pkv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:pkv_background="#ffffff"
        app:pkv_hint="- - - -"
        app:pkv_hintColor="#000000"
        app:pkv_itemBackground="@drawable/rectangle_box"
        app:pkv_itemHeight="6"
        app:pkv_maxLength="4"
        app:pkv_textColor="@color/colorPrimaryDark"
        app:pkv_textSize="18dp"
        app:pkv_textStyle="BOLD_ITALIC"
        app:pkv_viewEnabled="true" />
        
 For validation :
  
        if (pkv.isValidated()) {
            tv_result.setText("Result :\n" + pkv.getProductKeyText());
        } else {
            Toast.makeText(this, "Fields cannot be blank.", Toast.LENGTH_SHORT).show();
        }
 
  
  
