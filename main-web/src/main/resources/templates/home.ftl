<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
<h2>홈페이지</h2>
<h2>메뉴리스트</h2>

<#macro displayMenu menuList>
    <#list menuList as item>
        <ul>
            <li>menuId: ${(item.id)!"empty"}</li>
            <li>pageUrl: ${(item.pageUrl)!"empty"}</li>
            <li>menuName: ${(item.menuName)!"empty"}</li>
            <li>subMenuList</li>
            <@displayMenu item.menuList />
        </ul>
    </#list>
</#macro>

<@displayMenu menuList/>
</body>