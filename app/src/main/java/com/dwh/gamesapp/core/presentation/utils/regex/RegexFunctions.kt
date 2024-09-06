package com.dwh.gamesapp.core.presentation.utils.regex

object RegexFunctions {
    fun String.isNumber():Boolean {
        return this.matches(Regex("[0-9]{1,64}"))
    }
    fun String.isEmail():Boolean{
        return this.matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}+(\\s?)"))
    }
    fun String.containsLetter() : Boolean {
        return this.matches(Regex("^(?=.*[a-z])(?=.*[A-Z]).+$"))
    }
    fun String.containsNumber() : Boolean {
        return this.matches(Regex("^(?=.*[0-9]).+\$"))
    }

    fun String.containsSpecialCharacter() : Boolean {
        return this.matches(Regex("^(?=.*[@#&*-+=%_:;/,.¿¡?!]).+\$"))
    }
    /*fun String.isLetter():Boolean{
        //return this.matches(Regex("([a-zA-ZñÑáéíóúÁÉÍÓÚ])+(?:\\s+[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)+")) (nombre)
        return this.matches(Regex("[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z]?[a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+(\\s+[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z]?[a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+)*\$"))
    }
    fun String.surnames():Boolean{ *//*(apellidos)*//*
        return this.matches(Regex("^[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z][a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+(\\s+[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z]?[a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+)*\$"))
    }
    fun String.isFirstCapitalLetter():Boolean{
        return this.matches(Regex("^[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z][a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+"))
    }
    fun String.isDecimalNumber():Boolean{
        return  this.matches(Regex("^[0-9]+(\\s?)+(.[0-9]+(\\s?)+)?$"))
    }
    fun String.isSpecialCharacters():Boolean {
        return  this.matches(Regex("^[<>{}@#&*-+=()%_:;/,.£€¥¢©®™¿¡?! ¦¬×§¶°~|]+\$"))
    }*/
}