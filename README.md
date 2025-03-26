# Type Driven Event Bus


# @TypeMagnetEvent
```kotlin
        // 🚀 Event Publish
	val crollerEvent = new CrollerEvent()
 	crollerEvent.data = "@TypeMagnetEvent Annotation 이붙은 메서드중 CrollerEvent Type과 일치하는 메소드에게 
 	MagnetEvent.getInstance().postTypeChannel(crollerEvent)

	// 🟢 CrollerEvent Type Match -> Subscribe OK 
	@TypeMagnetEvent
	public void listenTypeEvent(crollerEvent CrollerEvent){
		tvGod.setText(crollerEvent.getData());
	}
	
	// 🔴 Type Miss Match ->  No Subscribe
	@TypeMagnetEvent
	public void listenTypeEvent(godEvent GodEvent){
		tvGod.setText(godEvent.getData());
	}
```

# @BrodCastEvent
```kotlin
Pub
 // 🚀 Event Publish 
 btnclean.setOnClickListener { _ ->MagnetEvent.getInstance().postBrodCast(BrodCastPacket().setEvent(EVENT_CLEAN_FRAGMENT))}

 // 🟢 all Subscribe
 @BrodCastEvent
    fun listenEvent(brodCastPacket: BrodCastPacket) {
        when (brodCastPacket.eventType) {
            MainActivity.EVENT_HI_FRAGMENT -> godTv.text="hi!! I'am [TOP LEFT] Fragment !!"
            MainActivity.EVENT_CLEAN_FRAGMENT -> godTv.text="Listen Clean"
        }
    }
```
