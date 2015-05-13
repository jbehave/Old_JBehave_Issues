
Scenario: default behaviour is to replace the value anywhere it shows up

Given a parametrised table:
|name|value|message|
|name1|scheme|This is for scheme|

Examples:
|scheme| 
|scheme1| 
|scheme2|
|scheme3|

Scenario: with default behaviour, replacement needs to be ordered by size of named parameter

Given a parametrised table:
|name|value|message|
|name1|scheme|scheme_alias is an alias for scheme|

Examples:
|scheme|scheme_alias|
|scheme1|alias1|
|scheme2|alias2|
|scheme3|alias3|

Scenario: overridden behaviour (per table or ParameterControls) to replace whole words only

Given a parametrised table:
{replaceWholeWordValue=true}
|name|value|value_alias|
|name1|scheme|scheme2|

Examples:
|scheme|scheme_alias|
|scheme1|scheme_alias1|
|scheme2|scheme_alias2|
|scheme3|scheme_alias3|


