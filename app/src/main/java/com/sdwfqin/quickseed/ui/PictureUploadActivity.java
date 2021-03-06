package com.sdwfqin.quickseed.ui;

import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.sdwfqin.quicklib.base.BaseActivity;
import com.sdwfqin.quicklib.view.pictureupload.PictureUpModel;
import com.sdwfqin.quicklib.view.pictureupload.PictureUploadCallback;
import com.sdwfqin.quicklib.view.pictureupload.PictureUploadView;
import com.sdwfqin.quickseed.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述：九宫格上传图片
 *
 * @author zhangqin
 * @date 2018/5/31
 */
public class PictureUploadActivity extends BaseActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.pic)
    PictureUploadView mPic;

    @Override
    protected int getLayout() {
        return R.layout.activity_picture_upload;
    }

    @Override
    protected void initEventAndData() {
        mTopbar.setTitle("九宫格上传图片");
        mTopbar.addLeftBackImageButton()
                .setOnClickListener(v -> finish());

        mPic.setPicUploadCallback(new PictureUploadCallback() {
            @Override
            public void remove(int position, List list) {

            }

            @Override
            public void click(int position, PictureUpModel pictureUpModel, List list) {

            }

            @Override
            public void onAddPic(int maxPic, List list) {
                PictureSelector.create(mContext)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(maxPic)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(4)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .enableCrop(false)// 是否裁剪 true or false
                        .compress(true)// 是否压缩 true or false
                        .isGif(false)// 是否显示gif图片 true or false
                        // .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<PictureUpModel> models = new ArrayList<>();
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        models.add(new PictureUpModel(selectList.get(i).getPath()));
                    }
                    mPic.setAddData(models);
                    break;
            }
        }
    }
}
