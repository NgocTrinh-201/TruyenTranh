package com.ak.doctruyenchu.Constans;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Constans {
    public static final int LAY_TRUYEN_MOI_NHAT = 1;
    public static final int LAY_TRUYEN_DE_CU = 2;
    public static final int LAY_TOP10_TRUYEN_MOI_NHAT = 3;
    public static final int LAY_TRUYEN_THEO_TEN_TAC_GIA =4;
    public static final int VIEW_GIOI_THIEU_TRUYEN = 5;
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();
    public static final int SELECT_IMAGE = 6;
    public static final String AUTHOR = "Author";
    public static final String READER = "Reader";
    public static final String LICH_SU = "LICH_SU";
    public static final String CHUONG = "CHUONG";
    public static final String TRUYEN = "TRUYEN";
    public static final String USER = "USER";
    public static final String BINH_LUAN = "BINH_LUAN";
    public static final String DANH_GIA = "DANH_GIA";
    public static final String QL_DANG_TRUYEN = "QL_DANG_TRUYEN";
    public static final String DA_PUBLIC = "DA_PUBLIC";
    public static final String CHUA_PUBLIC = "CHUA_PUBLIC";
    public static final String DE_CU = "DE_CU";
    public static final String TU_TRUYEN = "TU_TRUYEN";
    public static final String KEY = "KEY";
    public static final String PICTURE = "PICTURE";
    public static final String ANH_BIA_TRUYEN = "ANH_BIA_TRUYEN";
    public static final String TEN_TRUYEN_KEY = "TEN_TRUYEN_KEY";
    public static final String URL_ANH_NEN_TRUYEN_KEY = "URL_ANH_NEN_TRUYEN_KEY";
    public static final String QUAN_LY_DANG_TRUYEN = "QUAN_LY_DANG_TRUYEN";
    public static final String TAC_GIA_KEY = "TAC_GIA_KEY";
    public static final String TRUYEN_DE_CU = "Truyện đề cử";
    public static final String TRUYEN_MOI_NHAT = "Truyện mới nhất";
    public static final String TRUYEN_CUNG_TAC_GIA = "Truyện cùng tác giả";
    public static final String LICH_SU_DOC_TRUYEN = "Lịch sử đọc truyện";
    public static final String TRUYEN_CAT_GIU = "Truyện cất giữ";
    public static final String TRUYEN_TOP_RATE = "Truyện top rate";
    public static final String TRUYEN_TOP_VIEW = "Truyện top view";
    public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance("https://doc-truyen-chu-default-rtdb.asia-southeast1.firebasedatabase.app/");
    public static final FirebaseStorage STORAGE = FirebaseStorage.getInstance("gs://doc-truyen-chu.appspot.com");
}
