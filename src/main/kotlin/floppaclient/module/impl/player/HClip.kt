package floppaclient.module.impl.player

import floppaclient.module.Category
import floppaclient.module.Module
import floppaclient.module.impl.player.Clip.hClip
import floppaclient.module.settings.impl.NumberSetting

object HClip : Module(
    "H Clip",
    category = Category.PLAYER,
    description = "Clips you with the specified settings. From testing it seems like you need a y offset value of at least -1.7 and the maximum distance you can go is 7.9."
){
    private val hDist = NumberSetting("Distance", 7.5,0.0,9.9,0.1)
    private val yOffs = NumberSetting("Y Offset", -1.7,-5.0,2.0,0.1)

    init {
        this.addSettings(
            hDist,
            yOffs
        )
    }

    override fun keyBind() {
        if (this.enabled) {
            hClip(hDist.value, Clip.yaw(), yOffs.value)
        }
    }
}