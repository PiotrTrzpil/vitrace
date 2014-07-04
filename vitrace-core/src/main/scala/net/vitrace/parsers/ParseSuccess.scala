package net.vitrace.parsers


trait ParseLineResult extends Serializable{

}

case class ParseSuccess(sourceLine : String, logLine : LogLine) extends ParseLineResult{

}


case class ParseFailure(sourceLine : String, errorMessage : String) extends ParseLineResult{

}
