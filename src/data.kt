import org.json.JSONArray
import org.json.JSONObject

data class Data(val obj: JSONObject){
  fun getOneScene(scene: String): JSONObject {
    return obj.optJSONObject(scene)
  }
  fun getScenes(): JSONArray {
    return obj.names()
  }
}

class Game(
  private val scenes_names: JSONArray,
  private var current_scene: String,
  private val scenes: MutableList<Scene> = mutableListOf(),
  private var inventory: MutableList<Inventory> = mutableListOf(),
  private var playing: Boolean = false,
  private var game: Boolean = false
) {
  fun gameToJson(): JSONObject {
    return JSONObject(
      """{"scenes_names": $scenes_names, "current_scene": $current_scene, "scenes": ${getAllScenes()}, "inventory": ${getInventory()}}"""
    )
  }
  private fun getAllScenes(): MutableList<JSONObject> {
    val scenesList: MutableList<JSONObject> = mutableListOf()

    for (scn in scenes) {
      scenesList.add(scn.sceneToJson())
    }

    return scenesList
  }
  fun getInventory(): MutableList<String> {
    val inventoryList: MutableList<String> = mutableListOf()

    for (inv in inventory) {
      inventoryList.add(inv.getInvKey())
    }

    return inventoryList
  }
  fun addToInventory(sceneObj: String) {
    inventory.add(Inventory(sceneObj))
  }
  fun removeFromInventory(invObj: String) {
    for (inv in inventory) {
      if (inv.getInvKey() == invObj) {
        inventory.remove(inv)
      }
    }
  }
  fun isInInventory(sceneObj: String): Boolean {
    for (inv in getInventory()) {
      if (inv == sceneObj) {
        return true
      }
    }
    return false
  }
  fun getScenes(scene: String): Scene? {
    for (scn in scenes) {
      if (scn.getSceneKey() == scene) {
        return scn
      }
    }
    return null
  }
  fun getActualScenes(): Scene? {
    for (scn in scenes) {
      if (scn.getSceneKey() == current_scene) {
        return scn
      }
    }
    return null
  }
  fun isPlaying(): Boolean {
    return playing
  }
  fun setPlaying() {
    playing = true
  }
  fun setNotPlaying() {
    playing = false
  }
  fun havingGame(): Boolean {
    return game
  }
  fun setGame() {
    game = true
  }
  fun setCurrentScene(scene: String) {
    current_scene = scene
  }
  fun addScene(key: String, name: String, description: String) {
    scenes.add(Scene(key, name, description))
  }
  fun getNextScene(target: String): Scene? {
    setCurrentScene(target)
    return getActualScenes()
  }
  fun loadGame(scene: String, sceneObj: String) {
    val loadScene = getScenes(scene)

    val getObj = loadScene?.getObj(sceneObj)

    if (getObj != null) {
      loadScene?.setSceneCollObj(getObj)
    }
  }
}

class Scene(
  private val key: String,
  private val name: String,
  private val description: String,
  private val objects: MutableList<GameObject> = mutableListOf()
) {
  fun setSceneCollObj(sceneObj: GameObject) {
    objects.remove(sceneObj)
  }
  fun sceneToJson(): JSONObject {
    return JSONObject(
      """{"$key": ${getSceneObjs()}}"""
    )
  }
  fun getSceneKey(): String {
    return key
  }
  fun getSceneName(): String {
    return name
  }
  fun getSceneDescription(): String {
    return description
  }
  fun addObj(
    key: String,
    type: String,
    description: String,
    status: JSONObject,
    properties: JSONObject,
    target: String
  ) {
    objects.add(GameObject(key, type, description, status, properties, target))
  }
  fun getObj(sceneObj: String): GameObject? {
    for (obj in objects) {
      if (obj.getObjKey() == sceneObj.uppercase()) {
        return obj
      }
    }
    return null
  }
  private fun getSceneObjs(): MutableList<String> {
    val objList: MutableList<String> = mutableListOf()

    for (obj in objects) {
      objList.add(obj.getObjKey())
    }
    return objList
  }
  fun isCollectObj(sceneObj: GameObject): Boolean {
    if (sceneObj.getObjType() == "COLLECT_OBJ") {
      return true
    }
    return false
  }
  fun isObj(sceneObj: String): Boolean {
    for (obj in getSceneObjs()) {
      if (obj == sceneObj) {
        return true
      }
    }
    return false
  }
}

class GameObject(
  private val key: String,
  private var type: String,
  private val description: String,
  private val status: JSONObject,
  private val properties: JSONObject,
  private val target: String,
) {
  fun getPropertiesNamesList(): MutableList<String> {
    val propsList: MutableList<String> = mutableListOf()

    if (getPropertiesNames() != null) {
      for (index in 0 until getPropertiesNames()!!.length()) {
        val prop: String = getPropertiesNames()!![index] as String

        propsList.add(prop)
      }
    }
    return propsList
  }
  private fun getPropertiesNames(): JSONArray? {
    if (properties.length() > 0) {
      return properties.names()
    }
    return null
  }
  fun getOneProperties(prop: String): JSONObject {
    return properties.optJSONObject(prop)
  }
  fun getObjKey(): String {
    return key
  }
  fun getObjType(): String {
    return type
  }
  fun getObjDescription(): String {
    return description
  }
  fun getPositiveStatus(): String {
    return status.optString("positive")
  }
  fun getNegativeStatus(): String {
    return status.optString("negative")
  }
  fun hasPositiveStatus(): Boolean? {
    if (status.optString("negative") == "NONE" && status.optString("positive") != "NONE") {
      return true
    } else if (status.optString("positive") == "NONE" && status.optString("negative") != "NONE") {
      return false
    }
    return null
  }
  fun getObjTarget(): String {
    return target
  }
  fun hasTarget(): Boolean? {
    if (target == "") return false
    else if (target != "") return true
    return null
  }
}

class Inventory(
  private val items: String
) {
  fun getInvKey(): String {
    return items
  }
}
