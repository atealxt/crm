<#macro page>
<#escape x as x?html>
<#if mailSentInfo?size != 0>

    <h2>发送报告</h2>
    <p>
        <div>总发送数：<span id="spanTotal">${total}</span></div>
        <div>成功数：<span id="spanSuccess">${success}</span></div>
    </p>
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