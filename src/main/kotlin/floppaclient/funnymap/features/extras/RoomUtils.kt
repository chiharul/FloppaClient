package floppaclient.funnymap.features.extras

import floppaclient.FloppaClient
import floppaclient.funnymap.core.*
import floppaclient.utils.Utils.equalsOneOf
import net.minecraft.util.BlockPos

object RoomUtils {
    /**
     * Rotates the given blockPos inside of a room with rotation to rotation 0.
     */
    fun getRotatedPos(blockPos: BlockPos, rotation: Int): BlockPos {
        return when {
            rotation.equalsOneOf(90, -270) -> BlockPos(-blockPos.z, blockPos.y, blockPos.x)
            rotation.equalsOneOf(180, -180) -> BlockPos(-blockPos.x, blockPos.y, -blockPos.z)
            rotation.equalsOneOf(270, -90) -> BlockPos(blockPos.z, blockPos.y, -blockPos.x)
            else -> blockPos
        }
    }

    /**
     * Rotates all blockPos in the given set inside of a room with rotation to rotation 0.
     */
    fun getRotatedPosSet(posSet: MutableSet<BlockPos>, rotation: Int): MutableSet<BlockPos> {
        val returnSet: MutableSet<BlockPos> = mutableSetOf()
        posSet.forEach {
            returnSet.add(getRotatedPos(it, rotation))
        }
        return returnSet
    }

    /**
     * Translates the given blockpos to the corresponding relative position in the room.
     */
    fun getRelativePos(blockPos: BlockPos, roomPair: Pair<Room, Int>): BlockPos {
        return getRotatedPos(blockPos.add(-roomPair.first.x, 0, -roomPair.first.z), -roomPair.second)
    }

    /**
     * Translates the given room relative coordinates to real coordinates.
     */
    fun getRealPos(blockPos: BlockPos, roomPair: Pair<Room, Int>): BlockPos {
        return getRotatedPos(blockPos, roomPair.second).add(roomPair.first.x,0,roomPair.first.z)
    }

    /**
     * Gets the extras data for the specified room (this can also be a region).
     *
     * If none exits it will create a blank entry.
     * @see getRoomExtrasData
     */
    fun getOrPutRoomExtrasData(room: Room): ExtrasData {
        return if (room.data.type == RoomType.REGION)
            FloppaClient.extras.extraRegions.getOrPut(room.data.name) {
                ExtrasData(room.core)
            }
        else
            FloppaClient.extras.extraRooms.getOrPut(room.data.name) {
                ExtrasData(room.core)
            }
    }

    /**
     * Gets the extras data for the specified room (this can also be a region).
     *
     * If none exits it will return null.
     * @see getOrPutRoomExtrasData
     */
    fun getRoomExtrasData(room: Room): ExtrasData? {
        return if (room.data.type == RoomType.REGION)
            FloppaClient.extras.extraRegions[room.data.name]
        else
            FloppaClient.extras.extraRooms[room.data.name]
    }

    fun instanceBossRoom(floor: Int): Room {
        return Room(0,0, RoomData("Boss $floor", RoomType.BOSS, 0, 1, listOf(0), 0,0))
    }

    fun instanceRegionRoom(region: String): Room {
        return Room(0,0, RoomData(region, RoomType.REGION, 0, 1, listOf(0), 0,0))
    }
}