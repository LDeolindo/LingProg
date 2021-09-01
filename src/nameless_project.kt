import java.io.File
import org.json.JSONObject

import java.text.*

fun colors(): Map<String, String> {
  return mapOf(
    "STYLE_RESET" to "\u001B[0m",
    "CYAN_BOLD_BRIGHT" to "\u001B[1;96m",
    "CYAN_BRIGHT" to "\u001B[36m",
    "RED_BOLD_BRIGHT" to "\u001B[1;91m",
    "RED_BRIGHT" to "\u001B[31m",
    "BLACK_BRIGHT" to "\u001B[30m",
    "YELLOW_BOLD_BRIGHT" to "\u001B[1;93m",
    "YELLOW_BRIGHT" to "\u001B[33m",
    "BLUE_BOLD_BRIGHT" to "\u001B[1;94m",
    "BLUE_BRIGHT" to "\u001B[34m",
    "PURPLE_BRIGHT" to "\u001B[35m",
    "PURPLE_BOLD_BRIGHT" to "\u001B[1;95m",
  )
}

fun files(): Map<String, String> {
  return mapOf(
    "LOGO" to "./data/logo.txt",
    "GAME_DATA" to "./data/data.json",
    "INITIAL_SCENE" to "SCENE_01",
    "GAME_OVER" to "./data/end01.txt",
    "BLANK" to "./data/blank.txt",
    "HELP" to "./data/help.txt",
    "VICTORY" to "./data/vic.txt",
    "CONT" to "./data/cont.txt",
  )
}

fun readJSONFile(fileName: String): String{
  return File(fileName).readText()
}

fun readFile(fileName: String, TEXT_COLOR: String, TEXT_RESET: String)
  = File(fileName).forEachLine { println(TEXT_COLOR + it + TEXT_RESET) }

fun cleanScene()
= readFile(files()["BLANK"].toString(), colors()["CYAN_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())

fun commands(): Map<String, String> {
  return mapOf(
    "inventory" to "inventory ==> Lista os objetos no inventário.",
    "check" to "check {OBJETO} ==> Mostra a descrição do objeto.",
    "get" to "get {OBJETO} ==> Pega o objeto caso seja coletável.",
    "use with" to "use {OBJETO_INVENTARIO} with {OBJETO_CENA} ==> Usa objeto do inventário com o objeto da cena.",
    "use" to "use {OBJETO} ==> Esa objeto da cena.",
    "newgame" to "newgame ==> Incia um novo jogo.",
    "save" to "save {SAVE_NAME} ==> Salva o jogo.",
    "saved" to "saved ==> Lista todos os jogos salvos.",
    "load" to "load {LOAD_NAME} ==> Carrega um jogo salvo.",
    "stop" to "stop ==> Volta para o menu.",
  )
}

fun objectNotFound()
= run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Boa noite?! Eh, eh, é?. Objeto não encontrado! ${colors()["STYLE_RESET"]}") }

fun objectNotCollect()
        = run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Boa noite?! Eh, eh, é?. Objeto não coletável! ${colors()["STYLE_RESET"]}") }

fun objecCollectedSucc()
        = run { println("${colors()["BLUE_BOLD_BRIGHT"]}\n $ Éh isso, objeto coletado! ${colors()["STYLE_RESET"]}") }

fun sceneOrobjectNotFound()
        = run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Boa tarde?! Eh, eh, é?. Cena ou Objeto não encontrado! ${colors()["STYLE_RESET"]}") }

fun objectFromInven()
        = run { println("${colors()["BLACK_BRIGHT"]}\n /* Calma: Aí tu tu que demais. A vida é feita de escolhar: Cê poderia ter escolhido checar o objeto, mas preferiu coletar. Senão seria muito facil! */ ${colors()["STYLE_RESET"]}") }

fun emptyInventory()
        = run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Bom dia?! Eh, eh, é?. Você não possui item no inventário! ${colors()["STYLE_RESET"]}") }

fun objNotInInventory()
        = run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Alerta: Objeto não encontrado no inventário! ${colors()["STYLE_RESET"]}") }

fun objectAlreadyCollect()
        = run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Alerta: Objeto já coletado, jogue limpo pf! ${colors()["STYLE_RESET"]}") }

fun objNotCompatible()
        = run { println("${colors()["RED_BOLD_BRIGHT"]}\n $  Erro: Objeto não compativel para essa ação! ${colors()["STYLE_RESET"]}") }

fun commandNotFound()
= run { println("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Bom dia! Eh, faz parte. Porém opção inválida. Tente outra vez. ${colors()["STYLE_RESET"]}") }

fun fileNameNotFound()
= run { print("${colors()["YELLOW_BOLD_BRIGHT"]}\n $ Num 'tô entendendo. Eh, eu também não. Arquivo não encontrado! :( ${colors()["STYLE_RESET"]}") }

fun chooseValidCommand()
= run { print("${colors()["RED_BOLD_BRIGHT"]}\n $ Eaw? Use um comando válido aí! ${colors()["STYLE_RESET"]}") }

fun newGameConfirmation()
= run { print("${colors()["PURPLE_BRIGHT"]}\n $ Começar NOVO jogo? (s / N): ${colors()["STYLE_RESET"]}") }

fun stopConfirmation()
        = run { print("${colors()["PURPLE_BRIGHT"]}\n $ Volta para o menu? (s / N): *Salve antes ou seu progresso será perdido!* ${colors()["STYLE_RESET"]}") }

fun loadConfirmation()
        = run { print("${colors()["PURPLE_BRIGHT"]}\n $ Carregar jogo? (s / N): *Salve antes ou seu progresso atual será perdido!* ${colors()["STYLE_RESET"]}") }

fun choose()
= run { print("${colors()["RED_BOLD_BRIGHT"]}\n $ Escolha (1 2 3 4): ${colors()["STYLE_RESET"]}") }

fun showCommands()
= run { for ((idx, value) in commands()) println("${colors()["PURPLE_BRIGHT"]} $idx:${colors()["STYLE_RESET"]} $value") }

fun showHelp() {
  readFile(files()["HELP"].toString(), colors()["PURPLE_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())
  showCommands()
  print("${colors()["PURPLE_BRIGHT"]}\n $ Voltar (S / n): ${colors()["STYLE_RESET"]}")
  var herpi = true
  while (herpi) {
    val help = readLine()!!.split(" ").map { it }
    when (help[0]) {
      'S'.toString() -> {
        cleanScene()
        startGame()
      }
      'N'.toString() -> {
        herpi = false
        cleanScene()
        showHelp()
      }
      's'.toString() -> {
        cleanScene()
        startGame()
      }
      'n'.toString() -> {
        herpi = false
        cleanScene()
        showHelp()
      }
      "" -> {
        cleanScene()
        startGame()
      }
      else -> {
        commandNotFound()
        print("${colors()["PURPLE_BRIGHT"]}\n $ * Voltar (S / n): ${colors()["STYLE_RESET"]}")
      }
    }
  }
}

fun showCont() {
  readFile(files()["CONT"].toString(), colors()["PURPLE_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())
  savedGame()
  print("${colors()["PURPLE_BRIGHT"]}\n $ Digite: (LOAD nome do jogo ou VOLTAR para retornar ao menu) ${colors()["STYLE_RESET"]}")
  var herpi = true
  while (herpi) {
    val help = readLine()!!.split(" ").map { it }
    when (help[0]) {
      "load" -> {
        if (help.size == 2 && help[1].isNotEmpty() ) {
          loadGame(help[1])
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "voltar" -> {
        cleanScene()
        startGame()
      }
      "LOAD" -> {
        if (help.size == 2 && help[1].isNotEmpty() ) {
          loadGame(help[1])
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "VOLTAR" -> {
        cleanScene()
        startGame()
      }
      else -> {
        commandNotFound()
        print("${colors()["PURPLE_BRIGHT"]}\n $ * Digite o nome do jogo ou VOLTAR para retornar ao menu: ${colors()["STYLE_RESET"]}")
      }
    }
  }
}

fun showScene(id: String, name: String, description: String) {
  println("${id}: $name")
  println(description)

  println("\n[+] digite [#sos] para ajuda")
}

fun saveGame(content: Game, fileName: String) {
  val save: JSONObject = content.gameToJson()

  File("data/save/${fileName}.json").writeText(save.toString())

  println("${colors()["BLACK_BRIGHT"]}\n $ Game salvo! ${colors()["STYLE_RESET"]}")
}

fun loadGame(fileName: String) {
  val files = File("data/save").walkTopDown()
  val filesList: MutableList<String> = mutableListOf()

  for (file in files) {
    if (file.isFile) {
      filesList.add(file.nameWithoutExtension)
    }
  }

  if (fileName in filesList){
    val game = loadGame()

    val json = readJSONFile("data/save/${fileName}.json")
    val gameJson = JSONObject(json)

    val names = gameJson.names()
    if (names != null) {
        game.setCurrentScene(gameJson.optString("current_scene"))
        val inv = gameJson.optJSONArray("inventory")
        for (index in 0 until inv.length()) {
          val objKey = inv[index] as String

          game.addToInventory(objKey)

          val scn = gameJson.optJSONArray("scenes_names")
          for (idx in 0 until scn.length()) {
            val scene = scn[idx] as String
            game.loadGame(scene, objKey)
          }
        }
      cleanScene()
      game.setPlaying()
      game.setGame()
      game.getActualScenes()?.let { playingGame(it, game) }
    }
  }
  else {
    fileNameNotFound()
  }
}

fun savedGame() {
  val files = File("data/save").walkTopDown()

  println("${colors()["BLUE_BOLD_BRIGHT"]} [*] Seus jogos salvos:  ${colors()["STYLE_RESET"]}")
  println("${colors()["BLUE_BRIGHT"]}\n %-15s\tLast Modified  ${colors()["STYLE_RESET"]}".format("Name"))
  for (file in files) {
    if (file.isFile) {
      val lastModified: Long = file.lastModified()
      val format = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
      val t = format.format(lastModified)
      println("${colors()["BLUE_BRIGHT"]} %-15s\t${t}  ${colors()["STYLE_RESET"]}".format(file.nameWithoutExtension))
    }
  }
}

fun playingGame(scene: Scene, game: Game) {
  showScene(scene.getSceneKey(), scene.getSceneName(), scene.getSceneDescription())

  while (game.havingGame()) {

    print("${colors()["PURPLE_BOLD_BRIGHT"]}\n $ : ${colors()["STYLE_RESET"]}")
    val choose: List<String> = readLine()!!.split(" ").map { it }
    when (choose[0].lowercase()) {
      "#sos" -> {
        if (choose.size == 1 && choose[0].isNotEmpty() ) {
          showCommands()
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "use" -> {
        if (choose.size == 2 && choose[1].isNotEmpty() ) {
          val useObj = scene.getObj(choose[1])

          if (useObj != null) {
            if (useObj.hasPositiveStatus()!!) {
              if (useObj.hasTarget()!!) {
                cleanScene()
                if (useObj.getObjTarget() == "SCENE_17") {
                  readFile(files()["VICTORY"].toString(), colors()["PURPLE_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())
                }
                println("${colors()["BLUE_BOLD_BRIGHT"]}\n ${useObj.getPositiveStatus()} ${colors()["STYLE_RESET"]} \n")
                game.getNextScene(useObj.getObjTarget())?.let { playingGame(it, game) }
              } else if (!useObj.hasTarget()!!) {
                println("${colors()["BLUE_BOLD_BRIGHT"]}\n ${useObj.getPositiveStatus()} ${colors()["STYLE_RESET"]} \n")
              }
            } else if (!useObj.hasPositiveStatus()!!) {
              if (useObj.hasTarget()!!) {
                cleanScene()
                if (useObj.getObjTarget() == "SCENE_00") {
                  readFile(files()["GAME_OVER"].toString(), colors()["RED_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())
                }
                println("${colors()["RED_BOLD_BRIGHT"]}\n  ${useObj.getNegativeStatus()} ${colors()["STYLE_RESET"]} \n")
                game.getNextScene(useObj.getObjTarget())?.let { playingGame(it, game) }
              } else if (!useObj.hasTarget()!!) {
                println("${colors()["RED_BOLD_BRIGHT"]}\n  ${useObj.getNegativeStatus()} ${colors()["STYLE_RESET"]} \n")
              }
            }
          } else {
            objectNotFound()
          }
        }
        else if (choose.size == 4) {
          if (choose[1].isNotEmpty() && game.isInInventory(choose[1].uppercase())) {
            if (choose[2].isNotEmpty() && choose[2].lowercase() == "with" && choose[3].isNotEmpty()) {
              val useObj = scene.getObj(choose[3])

              if (useObj != null) {
                val propsNames = useObj.getPropertiesNamesList()

                if (choose[1].uppercase() in propsNames) {
                  val oneProp = useObj.getOneProperties(choose[1].uppercase())

                  if (oneProp.optString("type") == "joke") {
                    println("${colors()["BLACK_BRIGHT"]}\n * ${oneProp.optString("message")} * ${colors()["STYLE_RESET"]}")
                  }
                  if (oneProp.optString("type") == "help") {
                    println("${colors()["BLACK_BRIGHT"]}\n $ @ ${oneProp.optString("message")} @ \n ${colors()["STYLE_RESET"]}")
                  }
                  if (oneProp.optString("type") == "easy") {
                    game.removeFromInventory(choose[1].uppercase())
                    println("${colors()["BLACK_BRIGHT"]}\n $ ->> ${oneProp.optString("message")} <<- \n ${colors()["STYLE_RESET"]}")
                  }
                  if (oneProp.optString("type") == "tip") {
                    game.removeFromInventory(choose[1].uppercase())
                    println("${colors()["BLACK_BRIGHT"]}\n $ # ${oneProp.optString("message")} # \n ${colors()["STYLE_RESET"]}")
                  }
                  if (oneProp.optString("type") == "motivation") {
                    println("${colors()["BLACK_BRIGHT"]}\n $ $ ${oneProp.optString("message")} $ $ \n ${colors()["STYLE_RESET"]}")
                  }
                }
                else {
                  objNotCompatible()
                }
              }
              else {
                objectNotFound()
              }
            }
            else {
              commandNotFound()
              chooseValidCommand()
            }
          }
          else {
            objNotInInventory()
          }
        }
        else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "get" -> {
        if (choose.size == 2 && choose[1].isNotEmpty() ) {
          if (scene.isObj(choose[1].uppercase())) {
            val getObj = scene.getObj(choose[1].uppercase())
            if (getObj?.let { scene.isCollectObj(it) } == true) {
              game.addToInventory(choose[1].uppercase())
              scene.setSceneCollObj(getObj)
              objecCollectedSucc()
            }
            else {
              objectNotCollect()
            }
          } else {
            if (game.isInInventory(choose[1].uppercase())) {
              objectAlreadyCollect()
            }
            else {
              objectNotFound()
            }
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "check" -> {
        if (choose.size == 2 && choose[1].isNotEmpty() ) {
          if (choose[1].uppercase() == scene.getSceneKey()) {
            cleanScene()
            showScene(scene.getSceneKey(), scene.getSceneName(), scene.getSceneDescription())
          }
          else {
            val checkObj = scene.getObj(choose[1])

            if (checkObj != null) {
              println(checkObj.getObjDescription())
            }
            else {
              if (game.isInInventory(choose[1].uppercase())) {
                objectFromInven()
              }
              else {
                sceneOrobjectNotFound()
              }
            }
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "inventory" -> {
        if (choose.size == 1 && choose[0].isNotEmpty() ) {
          if (game.getInventory().isNotEmpty()) {
            for (inv in game.getInventory()) print("${colors()["PURPLE_BOLD_BRIGHT"]} $inv ")
            println("${colors()["STYLE_RESET"]}")
          }
          else {
            emptyInventory()
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "save" -> {
        if (choose.size == 2 && choose[1].isNotEmpty() ) {
          saveGame(game, choose[1])
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "saved" -> {
        if (choose.size == 1 ) {
          savedGame()
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "load" -> {
        if (choose.size == 2 && choose[1].isNotEmpty() ) {
          loadConfirmation()
          var new = true
          while (new) {
            val newGame = readLine()!!.split(" ").map { it }
            when (newGame[0]) {
              'S'.toString() -> {
                loadGame(choose[1])
              }
              'N'.toString() -> {
                new = false
              }
              's'.toString() -> {
                loadGame(choose[1])
              }
              'n'.toString() -> {
                new = false
              }
              "" -> {
                new = false
              }
              else -> {
                chooseValidCommand()
                loadConfirmation()
              }
            }
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "stop" -> {
        if (choose.size == 1 ) {
          stopConfirmation()
          var new = true
          while (new) {
            val newGame = readLine()!!.split(" ").map { it }
            when (newGame[0]) {
              'S'.toString() -> {
                cleanScene()
                startGame()
              }
              'N'.toString() -> {
                new = false
              }
              's'.toString() -> {
                cleanScene()
                startGame()
              }
              'n'.toString() -> {
                new = false
              }
              "" -> {
                new = false
              }
              else -> {
                chooseValidCommand()
                stopConfirmation()
              }
            }
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      "newgame" -> {
        if (choose.size == 1 ) {
          newGameConfirmation()
          var new = true
          while (new) {
            val newGame = readLine()!!.split(" ").map { it }
            when (newGame[0]) {
              'S'.toString() -> {
                cleanScene()
                val game = loadGame()
                game.setPlaying()
                game.setGame()
                game.getActualScenes()?.let { playingGame(it, game) }
              }
              'N'.toString() -> {
                new = false
              }
              's'.toString() -> {
                cleanScene()
                val game = loadGame()
                game.setPlaying()
                game.setGame()
                game.getActualScenes()?.let { playingGame(it, game) }
              }
              'n'.toString() -> {
                new = false
              }
              "" -> {
                new = false
              }
              else -> {
                chooseValidCommand()
                newGameConfirmation()
              }
            }
          }
        } else {
          commandNotFound()
          chooseValidCommand()
        }
      }
      else -> {
        commandNotFound()
        chooseValidCommand()
      }
    }
  }
}

fun startingGame(game: Game) {
  readFile(files()["LOGO"].toString(), colors()["CYAN_BOLD_BRIGHT"].toString(), colors()["STYLE_RESET"].toString())

  print("${colors()["CYAN_BRIGHT"]}\n $ Escolha: ${colors()["STYLE_RESET"]}")
  while (game.isPlaying()) {

    val choose = readLine()!!.split(" ").map { it }
    when (choose[0]) {
      '1'.toString() -> {
        cleanScene()
        game.setGame()
        game.getActualScenes()?.let { playingGame(it, game) }
      }
      '2'.toString() -> {
        cleanScene()
        showCont()
      }
      '3'.toString() -> {
        cleanScene()
        showHelp()
      }
      '4'.toString() -> {
        game.setNotPlaying()
      }
      else -> {
        choose()
      }
    }
  }
}

fun loadData(): Data {
  val json = readJSONFile(files()["GAME_DATA"].toString())
  val gameJson = JSONObject(json)

  return Data(gameJson)
}

fun loadGame(): Game {
  val data = loadData()

  val game = Game(data.getScenes(), "SCENE_01")

  for (index in 0 until data.getScenes().length()) {

    val scene: String = data.getScenes()[index] as String
    val oneScene = data.getOneScene(scene)
    game.addScene(
      scene,
      oneScene.optString("name"),
      oneScene.optString("description")
    )

    val objScene = game.getScenes(scene)
    val obj = oneScene.optJSONObject("object")
    val objKeys = obj.names()
    if (objKeys != null){
      for (idx in 0 until objKeys.length()) {

        val objKey = objKeys[idx] as String

        val getOneObj = obj.optJSONObject(objKey)
        objScene?.addObj(
          objKey,
          getOneObj.optString("type"),
          getOneObj.optString("description"),
          getOneObj.optJSONObject("status"),
          getOneObj.optJSONObject("properties"),
          getOneObj.optString("target")
        )
      }
    }

  }

  return game
}

fun startGame() {
  val game = loadGame()
  game.setPlaying()
  startingGame(game)
}

fun main() {
  startGame()
}