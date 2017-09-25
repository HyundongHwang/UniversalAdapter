<!-- TOC -->

- [UniversalAdapter](#universaladapter)
- [개요](#개요)
- [특징](#특징)
- [데모 영상](#데모-영상)
- [데모 apk 다운로드](#데모-apk-다운로드)
- [적용하기](#적용하기)
    - [gradle 설정](#gradle-설정)
    - [글, 댓글, 대댓글의 Item과 ViewHolder 만들기](#글-댓글-대댓글의-item과-viewholder-만들기)
    - [UniversalAdapter 설정](#universaladapter-설정)

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
- `UniversalAdapter.setAdditionalCallback` 으로 추가적인 이벤트 컨트롤을 할 수 있도록 함.

# 데모 영상
- ![](https://github.com/HyundongHwang/UniversalAdapter/raw/master/demo.gif)

# 데모 apk 다운로드
- https://github.com/HyundongHwang/UniversalAdapter/raw/master/com.hhd2002.universaladaptertest.apk

<br>
<br>
<br>

# 적용하기
## gradle 설정

- /buid.gradle

```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- /app/buid.gradle

```groovy
dependencies {
    implementation 'com.github.HyundongHwang:universaladapter:0.9.0'
}
```

## 글, 댓글, 대댓글의 Item과 ViewHolder 만들기

- ArticleItem

```java
public class ArticleItem {
    public String text;
}
```

- ArticleVh

```java
public class ArticleVh extends UniversalViewHolder {
    @BindView(R.id.tv_text)
    TextView _TvText;
    private ArticleCommentActivity.ICallback _parentCallback;

    public ArticleVh(View itemView) {
        super(itemView);
    }

    @Override
    public View inflateConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_article, parent, false);
        return convertView;
    }

    @Override
    public void findAllViews(Object parentCallback) {
        ButterKnife.bind(this, this.itemView);
        _parentCallback = (ArticleCommentActivity.ICallback) parentCallback;
    }

    @Override
    public void onBindViewHolder() {
        ArticleItem thisItem = (ArticleItem) this.item;
        _TvText.setText(thisItem.text);
    }

    @OnClick(R.id.btn_show_comments)
    public void onViewClicked() {
        _parentCallback.onClickShowComment();
    }
}
```

- CommentItem
- CommentVh
- CommentOfCommentItem
- CommentOfCommentVh

## UniversalAdapter 설정

- ArticleCommentActivity

```java
public class ArticleCommentActivity extends AppCompatActivity {

    @BindView(R.id.rv_obj)
    RecyclerView _RvObj;

    private UniversalAdapter _adapter;
    private ArticleCommentDAO _dao = new ArticleCommentDAO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_article_comment);
        ButterKnife.bind(this);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _RvObj.setLayoutManager(lm);

        ArrayList<Class> itemTypeList = new ArrayList<>();
        itemTypeList.add(ArticleItem.class);
        itemTypeList.add(CommentItem.class);
        itemTypeList.add(CommentOfCommentItem.class);

        ArrayList<Class<? extends UniversalViewHolder>> vhTypeList = new ArrayList<>();
        vhTypeList.add(ArticleVh.class);
        vhTypeList.add(CommentVh.class);
        vhTypeList.add(CommentOfCommentVh.class);

        _adapter = new UniversalAdapter(
                _RvObj,
                itemTypeList,
                vhTypeList
        );

        _adapter.setListener(new IUniversalListener() {
            @Override
            public void onClickItem(Object item, int position, View convertView) {
                String json = MyUtils.createGson().toJson(item);
                Toast.makeText(getBaseContext(), json, Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLastItem() {
            }
        });

        _adapter.setAdditionalCallback(new ICallback() {
            @Override
            public void onClickShowComment() {
                _loadComments();
                _adapter.notifyDataSetChanged();
            }
        });

        _adapter.getItems().add(_dao.get(0));
        _RvObj.setAdapter(_adapter);
    }

    private void _loadComments() {
        if (_adapter.getItems().size() == _dao.size())
            return;


        for (int i = 1; i < _dao.size(); i++) {
            Object item = _dao.get(i);
            _adapter.getItems().add(item);
        }
    }

    public interface ICallback {
        void onClickShowComment();
    }
}
```