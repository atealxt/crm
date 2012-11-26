<#macro page>
<#escape x as x?html>

<table>
    <tr>
        <td>
            邮件标题和内容支持参数：${r"${name}"} - 企业名称，${r"${contact}"} - 联系人
        </td>
    </tr>
    <tr>
        <td>
            例如：对于“Dear ${r"${name}"}, how are you”，企业 `MyFoundry` 生成的文字为：“Dear MyFoundry, how are you”。
        </td>
    </tr>
    <tr>
        <td>
            支持逻辑或关系，如对于“Dear ${r"${contact|name}"}, how are you”，企业 `MyFoundry` 有联系人 `Tom` 时生成的文字为：“Dear Tom, how are you”，没有联系人时生成的文字为：“Dear MyFoundry, how are you”。
        </td>
    </tr>
    <tr>
        <td>
            支持默认值，如对于“Dear ${r"${contact::Sir or Madam}"}, how are you”，企业 `MyFoundry` 有联系人 `Tom` 时生成的文字为：“Dear Tom, how are you”，没有联系人时生成的文字为：“Dear Sir or Madam, how are you”。
        </td>
    </tr>
    <tr>
        <td>
            <b>注意：</b>在给邮件内容添加参数的时候，不要给参数设置样式（如加粗），否则可能影响参数解析的正确性。非参数不受此限制。
        </td>
    </tr>
</table>

</#escape>
</#macro>