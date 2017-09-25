<!-- TOC -->

- [UniversalAdapter](#universaladapter)
- [개요](#개요)
- [특징](#특징)
- [데모 영상](#데모-영상)
- [데모 apk 다운로드](#데모-apk-다운로드)
- [적용하기](#적용하기)
    - [gradle 설정](#gradle-설정)
    - [글, 댓글, 대댓글 UI RecyclerView 만들기](#글-댓글-대댓글-ui-recyclerview-만들기)

<!-- /TOC -->

<br>
<br>
<br>

# UniversalAdapter
- RecyclerView Adapter 를 매번 구현할 필요 없음.

# 개요
- RecyclerView 컨트롤을 구현하면 항상 adapter를 구현하는데 이게 boilerplate코드가 아닌가 생각했고, java class type을 이용해서 재구현 필요없는 adapter를 제작할 수 있겠다 생각했음.
- 하는김에 LoadMore 컨트롤도 구현하기 쉽게 UI와 데이타 로드 부분만 구현하면 되도록 abstract method 으로 조금더 추상화 했음.

# 특징
- RecyclerView Adapter를 매번 새로 구현하지 않고 UniversalAdapter 구현체를 사용하며, Item과 ViewHolder만 구현해서 타입지정하도록 함.
- 아이템 종류가 여럿일때 처리하기 위해서 Item, ViewHolder 타입을 배열로 받아 매칭해서 처리함.
    - RecyclerView 의 header, footer 를 처리하는게 좀더 쉬워짐
- `IUniversalListener.onClickItem` 으로 아이템클릭을 처리하도록 함.
- LoadMore를 쉽게 구현하도록 `IUniversalListener.onLastItem`, `UniversalAdapter.setLoadMoreVhType` 를 지원하여, LoadMoreVh 를 만들어서 타입지정하고, 데이타 로딩하는 구현을 명확히 붙일수 있도록 해 두었음.

# 데모 영상
- ![](https://im.ezgif.com/tmp/ezgif-1-904792716c.gif)

# 데모 apk 다운로드
- https://github.com/HyundongHwang/UniversalAdapter/raw/master/com.hhd2002.universaladaptertest.apk

<br>
<br>
<br>

# 적용하기
## gradle 설정
## 글, 댓글, 대댓글 UI RecyclerView 만들기