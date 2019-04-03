TalkBox
-------------

#### Software Development Project

**TalkBox Simulator**
A piece of software that simulates the behaviour of any TalkBox device.
Has a user interface similar to that of the device.
The number of buttons and their functionality is configurable.
Is fully tested to behave as the hardware device.

The current version of TalkBox.jar requires the TalkBoxData folder for all the audio files and configuration.
Hence in order to work properly, *do not move* TalkBox.jar file in different location.

This software requires physical devices such as monitor(for user interface), audio input(to record, eg. microphone) and output(to play sound, eg. speaker). System requirements are stated in 'Requirements_Document.pdf' inside of [Documentation][2] folder

>**Get start:** Check out the [User Manual][1] for quick start


### UML diagrams

You can also render sequence diagrams like this:

```sequence
User->TalkBoxGUI: Press Sound Button
TalkBoxGUI->ConfigurationGUI: if(false) createConfigure()
TalkBoxGUI-->User: if(true)playSound()
User->TalkBoxGUI: Press Stop Button
TalkBoxGUI-->User: stopSound()
User->TalkBoxGUI: Pree Configure Button
TalkBoxGUI->ConfigurationGUI: createConfigure()
User->ConfigurationGUI: Press Sound Button
ConfigurationGUI->ConfigurationGUI: Disable other sound buttons
User->ConfigurationGUI: Select Audio from List
ConfigurationGUI->ConfigurationGUI: Enable Swap button
User->ConfigurationGUI: Click Swap
ConfigurationGUI-->User: Swap sound audio and list audio
User->ConfigurationGUI: Click Delete Button
ConfigurationGUI-->User: Remove audio from selected button
User->ConfigurationGUI: Click Image Button
ConfigurationGUI->ImageSelectorGUI: Create ImageGUI()
User->ImageSelectorGUI: Select Image
ImageSelectorGUI-->ConfigurationGUI: Add Image to Selected Button
User->ConfigurationGUI: Click record button
ConfigurationGUI->RecordGUI: Create RecordGUI()
User->RecordGUI: Press Record
RecordGUI->RecordGUI: StartRecording()<br>StopRecording()
RecordGUI-->ConfigurationGUI: Add audio and exit RecordGUI
User->ConfigurationGUI: Click Save/Save_as Button
ConfigurationGUI->SaveAsDialogue: Open SaveGUI()
User->SaveAsDialogue: Name and click save button
SaveAsDialogue-->ConfigurationGUI: Send save name and close SaveGUI
ConfigurationGUI->ConfigurationGUI: Save current set with new name
User->ConfigurationGUI: Click Exit Button
ConfigurationGUI-->TalkBoxGUI: Open TalkBoxGUI and refresh
User->TalkBoxGUI: Press Exit Button
TalkBoxGUI->User: Exit System
```

[1]: https://github.com/JacobJae/EECS2311_group5/blob/master/TalkBoxV2/doc/User_Manual_v2.pdf
[2]: https://github.com/JacobJae/EECS2311_group5/tree/master/TalkBoxV2/Documentation
