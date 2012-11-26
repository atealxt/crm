<#macro page>
<#escape x as x?html>

邮件标题和内容支持参数：${r"${name}"} - 企业名称，${r"${contact}"} - 联系人
例如：对于“Dear ${r"${name}"}, how are you”，企业 `MyFoundry` 生成的文字为：“Dear MyFoundry, how are you”。
支持逻辑或关系，如对于“Dear ${r"${contact|name}"}, how are you”，企业 `MyFoundry` 有联系人 `Tom` 时生成的文字为：“Dear Tom, how are you”，没有联系人时生成的文字为：“Dear MyFoundry, how are you”。
支持默认值，如对于“Dear ${r"${contact::Sir or Madam}"}, how are you”，企业 `MyFoundry` 有联系人 `Tom` 时生成的文字为：“Dear Tom, how are you”，没有联系人时生成的文字为：“Dear Sir or Madam, how are you”。

</#escape>
</#macro>