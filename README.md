# scb-merge-rule Test

# 1.Class Overview

### com.vince  
Main - the MAIN class for executable jar. Test run for story 1 / Story 2 with exact input / output as requirement  
InstrumentPublisher - Receive ref data instrument and merge into existing(if exists) internal instrument in map for publishing  

### com.vince.Common  
InternalInstrument - Data structure for internally published instrument  
Market - Enum to maintain all supported markets  

### com.vince.refdata  
BaseInstrument - Base instrument model from reference data externally. Supposed to be external jar dependency  
LMEInstrument - extends Base with additional LME fields  
PRIMEInstrument - extends Base with additional PRIME fields  

### com.vince.rule  
MergeRule - Functional Interface to allow flexibility on adding/removing merge rules  
MergeRuleFactory - Factory to provide defined merge rules. Now LME and PRIME rules are provided  

### test packages  
ConcurrentPublisherTest - stress test on multi-threading map operation  
Unit tests to test rule, model and publish  

# 2. Executable Jar  
### Under folder - scbtest/src/main/resources/  
cd to the jar folder  
java -jar scbtest.jar  
