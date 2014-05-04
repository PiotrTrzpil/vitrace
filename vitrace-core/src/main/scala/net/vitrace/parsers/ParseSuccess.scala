package net.vitrace.parsers


trait ParseLineResult {

}

case class ParseSuccess(sourceLine : String, logLine : LogLine) extends ParseLineResult{

}


case class ParseFailure(sourceLine : String, errorMessage : String) extends ParseLineResult{

}
