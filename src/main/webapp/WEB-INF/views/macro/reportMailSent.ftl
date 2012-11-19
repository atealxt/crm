<#macro page>
<#escape x as x?html>
<#if mailSentInfo?size != 0>

    <h2>发送报告</h2>
    <table>
        <thead>
          <th>企业名</th>
          <th>邮箱</th>
          <th>状态</th>
        </thead>
        <tbody>
            <#list mailSentInfo as a>
                <tr>
                    <td>${a.name}</td>
                    <td>${a.mail}</td>
                    <td>${a.status}</td>
                </tr>
            </#list>
        </tbody>
    </table>

</#if>
</#escape>
</#macro>