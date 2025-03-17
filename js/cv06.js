let jedi = "Skywalker"
const jedi2 = "Obi-wan Kenobi"
var jedi3 = "Luke Skywalker" // var je global

console.log(jedi)
console.log(jedi2)
console.log(jedi3)

jedi = "Anakin Skywalker"
jedi3 = "Dark Luke Skywalker"

console.log(`Jedi ${jedi}`)

const numberOfSiths = "1"
const numberOfJedi = 1

if (numberOfJedi == numberOfSiths){
    console.log("stejny")
}

if (numberOfJedi === numberOfSiths){
    console.log("stejny")
}

const jedis = [jedi, jedi2, jedi3]
console.log(jedis)

jedis.forEach(j => console.log(j))

jedis.map(j => `Jedi: ${j}`).forEach(j => console.log(j))

jedis.filter(j => !j.includes("Dart")).map(j => `Jedi: ${j}`).forEach(j => console.log(j))

if (jedis.includes(jedi)){
    console.log("Impostor")
}

const [jedi4, ...restOFJedis] = jedis
console.log(`Jedis: ${jedi4}, ${restOFJedis}`)

const jediJson ={
    name : "Luke Skywalker",
    age : 35,
    sword : {
        color : "blue",
        cc : null
    }
}

console.log(jediJson)

const jediJsonString = JSON.stringify(jediJson)

console.log(`Jedis: ${jediJsonString}`)

console.log(JSON.parse(jediJsonString))

const {age, ...rest} = jediJson

console.log(`FULL JSON: ${age}, ${jediJson.name}`)
console.log(`FULL JSON: ${JSON.stringify(rest)}`)

if (jediJson.sword.color === "blue") {
    console.log(`Lightsaber color: ${jediJson.sword.color}`)
}

if (jediJson.sword?.cc === "blue") {
    console.log(`Lightsaber color: ${jediJson.sword.cc}`)
}

function isJediArmed(jedi){
    return jedi.sword != null
}

const isJediArmedArrow = (jedi) => {
    return jedi.sword != null
}

console.log(isJediArmedArrow(jediJson))

console.log(isJediArmed(jediJson))

let jediJson2 = JSON.parse(jediJsonString)
jediJson2.sword = null
console.log(isJediArmed(jediJson2))

let restResponse = fetch("https://swapi.dev/api/people/1/ ")
    .then(response => response.json())
    .then(responseJson => console.log(responseJson))

const isMyFather = async (fatherid, jedi) => {
    const response = await fetch(`https://swapi.dev/api/people/${fatherid}`)
    const responseJson = await response.json()
    if (responseJson?.name === jedi){
        console.log("IS your father")
    }else {
        console.log("Is not your father")
    }

}

await isMyFather(4,"Anakin Skywalker")
