# modern-android-browser
This is android broser Application

Main Activity:
1. Structure
○ Input field: ​URL
○ 3 buttons: ​GO​, ​CAPTURE​, ​HISTORY
○ Webview component: ​WEBVIEW
2. Once we enter any URL into the ​URL​ field and press the ​GO​ button - the content should
be loaded into the  ​WEBVIEW
3. Once the content is loaded and we press the ​CAPTURE​ button - the ​WEBVIEW
screenshot should be captured and saved
4. The captured screenshot should only contain the ​WEBVIEW​ contents, no other visual
elements should be visible.
5. Once we press the ​HISTORY​ button - the app should switch to the ​Secondary Activity
Secondary Activity:
1. Structure
○ Input field: ​SEARCH
○ List component: ​HISTORY
■ Image component: ​IMAGE
■ Text label: ​URL
■ Text label: ​DATETIME
■ Button: ​DELETE
2. The list should be scrollable and searchable by URL using the ​SEARCH​ field
3. Once we press the ​DELETE ​button - the element should be deleted from the list
4. Once we press the ​IMAGE ​or ​URL ​- the app should switch to the ​Main Activity ​and load
the ​URL ​into the ​WEBVIEW ​automatically
○ NOTE:​ The ​WEBVIEW ​in the​ ​Main Activity ​should be replaced by the previously
stored image until the web page is fully loaded
