# 플러그인 아키텍쳐 프로토토입

Base 애플리케이션이 있고 커스터마징을 위해서 기존 소스를 수정하지 않고 새로운 메뉴를 추가하거나 화면을 변경하거나 backend 로직을 변경할 수 있도록 하기 위한 아키텍쳐 프로토타입을 정의한다.

## Role별로 메뉴 구성 변경
role에 따라서 접근할 수 있는 메뉴가 다를 수 있다. role별로 서로 다른 메뉴 구성을 가질 수 있도록 구현한다.
또한, 사용자가 메뉴가 보이지 않더라도 직접 url을 넣고 들어오는 경우에도 권한 확인을 한다.
### domain 모듈
* role
  * RoleService bean이 초기화될 때 resources/roles.yaml 파일에 정의된 role 정보를 읽어들인다.
* user
  * 사용자 정보는 기본적으로 DB에 있지만, 해당 사용자의 role은 roles.yaml에 정의된 role로 참조된다.
  * User 엔티티에 roleName이 있으며 해당 사용자의 role을 알기 위해서는 User 엔티티의 roleName 필드를 참조하면 된다.
  * 해당 샘플에서는 web application이 기동할 때 MainWebAppConfig에서 샘플 user들을 생성한다.
* menu
  * 메뉴는 resources/menu.yaml 파일에 정의된다.
  * 주요 필드들
    * id: 메뉴 id. id값은 unique 해야 한다.
    * pageUrl: 해당 메뉴를 클릭하였을 때 이동해야 하는 pageUrl.
    * menuBundleKey: 메뉴 이름인데 i18n 지원을 위해서 message bundle에서 참조할 key 이름이다. 따라서, 해당 언어에 맞는 messages_<lang>.properties 파일이 필요하다. 기본적으로 lang 파라미터에 따라서 언어코드가 바뀐다.
    * menuName: i18n 상관없이 메뉴 이름을 지정하고자 하는 경우에 정의한다.  
    * subMenu: 메뉴는 하위 메뉴를 가지며 하위 메뉴를 정의한다.
* page
  * 인증된 사용자만 접근할 수 있는 페이지를 정의한다.
  * menu.yaml에 특정 role만 접근할 수 있도록 정의된 페이지는 자동으로 인증 페이지로 등록된다.
  * 그렇지 않고 직접 트정 url에 대해서 인증 처리를 하고 싶으면 
* application 모듈
  * 
* web 모듈