# ${  subject.name() } extends ${ subject.superclassType().name() }



    classNode.setQualified(${subject.qualifiedName()});
    String comment= ${subject.commentText()};

    classNode.setAbstract(${subject.isAbstract()});
    classNode.setError(${subject.isError()});
    classNode.setException(${subject.isException()});
    classNode.setExternalizable(${subject.isExternalizable()});
    classNode.setIncluded(${subject.isIncluded()});
    classNode.setSerializable(${subject.isSerializable()});
    classNode.setScope("${util.scope}");

## TypeVariables

<#list subject.typeParameters() as typeVariable>
*  ${util.parseTypeParameter(typeVariable)}
</#list>


## Implemented interfaces

<#list subject.interfaceTypes() as interfaceType>
*  ${interfaceType.name()}
</#list>

## Constructor

<#list subject.constructors() as constructor>
*  ${constructor.name()}
</#list>

## Methods

<#list subject.methods() as method>
*  ${method.name()}
</#list>

## Fields

<#list subject.fields() as field>
*  ${field.name()}
</#list>

## Annotations
<#list subject.annotations() as annotationDesc>
*  ${annotationDesc.name()}
</#list>

## Tags

<#list subject.tags() as tag>
*  ${tag.name()}
</#list>

