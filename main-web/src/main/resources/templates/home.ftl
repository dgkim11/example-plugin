<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
<h2>홈페이지</h2>
<h2>메뉴리스트</h2>
<#import "/spring.ftl" as spring />

<#macro displayMenu menuList>
    <#list menuList as item>
        <ul>
            <li>menuId: ${(item.id)!"empty"}</li>
            <li>pageUrl: ${(item.pageUrl)!"empty"}</li>
            <li>menuName: <@spring.messageText item.menuBundleKey!"" item.menuName!""/></li>
            <li>subMenuList</li>
            <@displayMenu item.subMenuList />
        </ul>
    </#list>
</#macro>

<@displayMenu menuList/>
<form action="/doLogout" method="post">
    <button class="w-100 btn btn-dark btn-lg" type="submit">
        로그아웃
    </button>
</form>
</body>