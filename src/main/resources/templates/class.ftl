<#import "lib/java.ftl" as java>

`package` `${subject.containingPackage().name()}`

<#-- Base class/interface -->
${java.annotations_for(subject)}
# <@compress  single_line=true>${java.modifiers(subject)}  ${subject.typeName()}</@compress>
<#if subject.isClass()>

---
<#-- Class description -->
<@java.tags subject.inlineTags() />

<#-- All Class fields -->
<#if subject.fields()?has_content>
## Fields
  <#list subject.fields() as field>
*  ${field.name()} - ${field.commentText()}
  </#list>
</#if>

<#-- Constructor -->
<#list subject.constructors()>
## Constructor
<#items as constructor>
---
### ${constructor.name()}(<@java.parameterList constructor.parameters() />) <@java.exceptionList constructor.thrownExceptionTypes() />

<@java.tags constructor.inlineTags() />


<#if constructor.typeParameters()?has_content>
<@java.typeParameterWithTags util constructor.typeParameters()/>
</#if>
<#if constructor.paramTags()?has_content>
<@java.parameterTags constructor.paramTags() />
</#if>
</#items>

</#list>

${java.classHierarchyWithInterfaces(subject, " * ", true)}
<#elseif subject.isInterface() >
<#-- ${java.interfaceHierarchy(subject, " * extends ")} -->

${java.classHierarchyWithInterfaces(subject, " * ", true)}

</#if>


<#list subject.typeParameters()>
##  Type Parameters

|Name|Description|
|--------|---------------|
  <#items  as typeVariable>
| &lt;${typeVariable.name()}<#list typeVariable.bounds()> extends <#items  as bound>${java.link(bound)}<#sep> & </#sep></#items><#else></#list>&gt;| ${util.getTypeParamComment(typeVariable)} |
  </#items>
</#list>

<#-- Methods -->
## Methods
<#list subject.methods() as method>

### ${method.name()}

${java.annotations_for(method)} ${java.link(method.returnType())} ${method.name()}(<@java.parameterList method.parameters() />) <@java.exceptionList method.thrownExceptionTypes() />

<@java.tags method.inlineTags() />


<#if method.typeParameters()?has_content>
<@java.typeParameterWithTags util method.typeParameters()/>
</#if>

<#if method.paramTags()?has_content>
<@java.parameterTags method.paramTags() />
</#if>

  <#if method.throwsTags()?has_content>
#### Exceptions

TODO: ThrowsTag[]throwsTags();
  </#if>
</#list>