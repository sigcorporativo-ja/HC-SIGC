<#if ejb3?if_exists>
<#if pojo.isComponent()>
@${pojo.importType("javax.persistence.Embeddable")}
<#else>
@${pojo.importType("javax.persistence.Entity")}
@${pojo.importType("org.hibernate.envers.Audited")}
<#if clazz.table.name?length gt 25>@${pojo.importType("org.hibernate.envers.AuditTable")}("${clazz.table.name?substring(0, 25)}_HIST")</#if>
@${pojo.importType("javax.persistence.Table")}(name="${clazz.table.name}"<#assign uniqueConstraint=pojo.generateAnnTableUniqueConstraint()><#if uniqueConstraint?has_content>
    , uniqueConstraints = ${uniqueConstraint} 
</#if>)
</#if>
</#if>