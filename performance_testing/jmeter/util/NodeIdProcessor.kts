import java.io.File

val testPlanFile = File("OrderCreationRaw.jmx")

var testPlanText = testPlanFile.readText()
println("======= ORIGINAL PLAN: =======")
println(testPlanText)
println("==============================")

val file = File("variables.txt")

file.forEachLine { line ->
    val tokens = line.split(",")
    // Process tokens here
    println("Replacing nodes: " + tokens)
    val id = tokens[0].substring(4)//cut "all_" to get "idOf_<varName>" part only
    for (i in 1 until tokens.size) {
        testPlanText = testPlanText.replace("&quot;node&quot;:${tokens[i]},", "&quot;node&quot;:\${$id},")
    }
}


val resultPlanFile = File("OrderCreation_varsReplaced.jmx")
if (resultPlanFile.exists()) resultPlanFile.delete()
resultPlanFile.writeText(testPlanText)

println("======= CORRECTED PLAN: =======")
println(testPlanText)
println("===============================")
