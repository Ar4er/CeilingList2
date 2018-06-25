package com.teterin.teterin.ceilinglist.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.teterin.teterin.ceilinglist.R;
import com.teterin.teterin.ceilinglist.dialogs.ImageDialog;
import com.teterin.teterin.ceilinglist.dialogs.RenameDialog;
import com.teterin.teterin.ceilinglist.logic.CeilingLab;
import com.teterin.teterin.ceilinglist.logic.Materials;
import com.teterin.teterin.ceilinglist.logic.SuspendCeiling;
import com.teterin.teterin.ceilinglist.logic.details.Cd60;
import com.teterin.teterin.ceilinglist.logic.details.Connector;
import com.teterin.teterin.ceilinglist.logic.details.Detail;
import com.teterin.teterin.ceilinglist.logic.details.Lock;
import com.teterin.teterin.ceilinglist.logic.details.Panel;
import com.teterin.teterin.ceilinglist.logic.details.Screw;
import com.teterin.teterin.ceilinglist.logic.details.ScrewCeiling;
import com.teterin.teterin.ceilinglist.logic.details.ScrewPanel;
import com.teterin.teterin.ceilinglist.logic.details.ScrewWall;
import com.teterin.teterin.ceilinglist.logic.details.Suspend;
import com.teterin.teterin.ceilinglist.logic.details.Ud28;

import java.util.UUID;


/**
 * Created by ar4er25 on 3/21/2017.
 */

public class CardSetFragment extends Fragment {
    private static final String TAG = "CardSetFragment";
    public static final int REQUEST_NAME = 0;
    public static final String RENAME_DIALOG = "com.bignerdrunch.android.suspendseilingcalculator.CardSetFragment.RENAME_DIALOG";
    public static final String UUID_EXTRA = "com.bignerdrunch.android.suspendseilingcalculator.CardSetFragment.UuidExtra";
    private static final String IMAGE = "image";

    private TextView mNameTextView;
    private SuspendCeiling mSuspendCeiling;
    private Ud28 mUd28;
    private Cd60 mCd60;
    private Lock mLock;
    private Suspend mSuspend;
    private ScrewWall mScrewWall;
    private ScrewPanel mScrewPanel;
    private ScrewCeiling mScrewCeiling;
    private Connector mConnector;
    private Panel mPanel;
    private Materials mWallMaterial;
    private Materials mCeilingBaseMaterial;
    private Screw mConcreteScrew;
    private Screw mWoodScrew;
    private Screw mDwScrew;

    CalculationMessageCreater messageCreater;


    public static CardSetFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(UUID_EXTRA, uuid);
        CardSetFragment cardSetFragment = new CardSetFragment();
        cardSetFragment.setArguments(args);
        return cardSetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID args = (UUID) getArguments().getSerializable(UUID_EXTRA);
        mSuspendCeiling = CeilingLab.getCeilingLab(getActivity()).getSuspendCeiling(args);
        mUd28 = mSuspendCeiling.getUd28();
        mCd60 = mSuspendCeiling.getCd();
        mLock = mSuspendCeiling.getLock();
        mSuspend = mSuspendCeiling.getSuspend();
        mConnector = mSuspendCeiling.getConnector();
        mScrewWall = mSuspendCeiling.getScrewWall();
        mScrewCeiling = mSuspendCeiling.getScrewCeiling();
        mScrewPanel = mSuspendCeiling.getScrewPanel();
        mPanel = mSuspendCeiling.getPanel();
        mWallMaterial = mSuspendCeiling.getWallMaterial();
        mCeilingBaseMaterial = mSuspendCeiling.getCeilingBaseMaterial();
        mConcreteScrew = mSuspendCeiling.getScrewConcrete();
        mWoodScrew = mSuspendCeiling.getScrewsWood();
        mDwScrew = mSuspendCeiling.getScrewsDryWolls();

        messageCreater = new CalculationMessageCreater(mSuspendCeiling);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager_item, container, false);
        //Ads.showBottomBanner(getActivity(), v);
        CardView cdCardView = (CardView) v.findViewById(R.id.cd_card_view);

        TextView areaTextView = (TextView) v.findViewById(R.id.area_info_text_view);
        areaTextView.setText(mSuspendCeiling.areaToString());
        TextView sizesTextView = (TextView) v.findViewById(R.id.sizes_info_text_view);
        sizesTextView.setText(mSuspendCeiling.toString());
        TextView cdStepTextView = (TextView) v.findViewById(R.id.cd_step_info_text_view);
        cdStepTextView.setText(String.format(getString(R.string.format_cd_step_string), mSuspendCeiling.getCdStep()));
        TextView panelLayersTextView = (TextView) v.findViewById(R.id.panel_layers_info_text_view);
        panelLayersTextView.setText(isOneLayer(mSuspendCeiling.getPanelLayers()) ? R.string.panel_layer_1 : R.string.panel_layer_2);

        TextView firstCdSizeTitleTextVew = (TextView) v.findViewById(R.id.first_size_cd_text);
        TextView firstCdSizeCountTextView = (TextView) v.findViewById(R.id.first_size_cd_count_text);

        if (mCd60.getCdCountOf4Size().getCount() == 0) {
            firstCdSizeTitleTextVew.setText(isSizeEquals3(mCd60.getCdCountOf3Size().getCount()) ? R.string.sub_title_size_3 : R.string.sub_title_size_4);
            firstCdSizeCountTextView.setText(detailToString(mCd60));
            TableLayout cdCardLayout = (TableLayout) v.findViewById(R.id.card_set_size_4m_Layout);
            cdCardLayout.removeAllViews();

            Log.i(TAG, "remove item");
        } else {
            firstCdSizeTitleTextVew.setText(R.string.sub_title_size_3);
            firstCdSizeCountTextView.setText(detailToString(mCd60.getCdCountOf3Size()));
            TextView secondSizeTitleTextView = (TextView) v.findViewById(R.id.secend_size_cd_text);
            TextView secondSizeCountTextView = (TextView) v.findViewById(R.id.secend_size_cd_count_text);
            secondSizeTitleTextView.setVisibility(View.VISIBLE);
            secondSizeCountTextView.setVisibility(View.VISIBLE);
            secondSizeTitleTextView.setText(R.string.sub_title_size_4);
            secondSizeCountTextView.setText(detailToString(mCd60.getCdCountOf4Size()));
        }
        TextView PanelSizeTextView = (TextView) v.findViewById(R.id._size_panel_text);
        PanelSizeTextView.setText(String.format(getString(R.string.format_panel_size_string), mSuspendCeiling.getPanelSize()));

        TextView udCountTextView = (TextView) v.findViewById(R.id.card_set_ud_count_text);
        udCountTextView.setText(detailToString(mUd28));
        TextView lockCountTextView = (TextView) v.findViewById(R.id.card_set_lock_count_text);
        lockCountTextView.setText(detailToString(mLock));
        TextView suspendCountTextView = (TextView) v.findViewById(R.id.card_set_suspend_count_text);
        suspendCountTextView.setText(detailToString(mSuspend));
        TextView connectorTextView = (TextView) v.findViewById(R.id.card_set_connector_count_text);
        connectorTextView.setText(detailToString(mConnector));
        TextView panelCountTextView = (TextView) v.findViewById(R.id.card_set_panel_count_text);
        panelCountTextView.setText(detailToString(mPanel));
        TextView mScrewConcreteTextView = (TextView) v.findViewById(R.id.card_set_screw_concrete_count_text);
        mScrewConcreteTextView.setText(detailToString(mConcreteScrew));
        TextView mScrewWoodTextView = (TextView) v.findViewById(R.id.card_set_screw_wood_count_text);
        mScrewWoodTextView.setText(detailToString(mWoodScrew));
        TextView mScrewDrywallTextView = (TextView) v.findViewById(R.id.card_set_screw_drywall_count_text);
        mScrewDrywallTextView.setText(detailToString(mDwScrew));

        TextView mScrewPanelTextView = (TextView) v.findViewById(R.id.first_size_dwscrew_count_text);
        if (mScrewPanel.getCount() != 0) {
            mScrewPanelTextView.setText(detailToString(mScrewPanel));
            TableLayout dWScrews40mmLayout = (TableLayout) v.findViewById(R.id.card_set_table_Layout_drywallscrews_secend_size);
            dWScrews40mmLayout.removeAllViews();
        } else {
            mScrewPanelTextView.setText(detailToString(mScrewPanel));
            mScrewPanelTextView.setText(detailToString(mScrewPanel.getFirstLayerScrews()));
            TextView mScrewPanelsecendLayer = (TextView) v.findViewById(R.id.secend_size_dwscrew_count_text);
            mScrewPanelsecendLayer.setText(detailToString(mScrewPanel.getSecondLayerScrews()));
        }


        mNameTextView = (TextView) v.findViewById(R.id.name_info_text_view);
        mNameTextView.setText(mSuspendCeiling.getName());
        ImageView cdImageView = (ImageView) v.findViewById(R.id.image_cd);
        cdImageView.setImageResource(R.drawable.cd_profiil);
        ImageView udImageView = (ImageView) v.findViewById(R.id.image_ud);
        udImageView.setImageResource(R.drawable.ud_image);
        ImageView lockImageView = (ImageView) v.findViewById(R.id.image_lock);
        lockImageView.setImageResource(R.drawable.lock);
        ImageView suspendImageView = (ImageView) v.findViewById(R.id.image_suspend);
        suspendImageView.setImageResource(R.drawable.suspend);
        ImageView panelImageView = (ImageView) v.findViewById(R.id.image_panel);
        panelImageView.setImageResource(R.drawable.panel);
        ImageView screwConcreteImageView = (ImageView) v.findViewById(R.id.image_screw_concrete);
        screwConcreteImageView.setImageResource(R.drawable.screw_concrete);
        ImageView screwWoodImageView = (ImageView) v.findViewById(R.id.image_screw_wood);
        screwWoodImageView.setImageResource(R.drawable.samorez);
        ImageView screwPanelImageView = (ImageView) v.findViewById(R.id.image_screw_panel);
        ImageView screwDwImageView = (ImageView) v.findViewById(R.id.image_screw_drywall);
        screwDwImageView.setImageResource(R.drawable.screw_panel);
        screwPanelImageView.setImageResource(R.drawable.screw_panel);
        ImageView connecterImageView = (ImageView) v.findViewById(R.id.image_connector);
        connecterImageView.setImageResource(R.drawable.connecter);
        CardView infoCardView = (CardView) v.findViewById(R.id.info_card_view);
        infoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                RenameDialog dialog = RenameDialog.newInstance(mSuspendCeiling.getName());
                dialog.setTargetFragment(CardSetFragment.this, REQUEST_NAME);
                dialog.show(fm, RENAME_DIALOG);
            }
        });

        CardView udCardView = (CardView) v.findViewById(R.id.ud_card_view);
        udCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mUd28), R.string.title_ud, R.drawable.ud_image));
            }
        });

        cdCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mCd60), R.string.title_cd, R.drawable.cd_profiil));
            }
        });
        CardView lockCardView = (CardView) v.findViewById(R.id.lock_card_view);
        lockCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mLock), R.string.title_locks, R.drawable.lock));
            }
        });
        CardView suspendCardView = (CardView) v.findViewById(R.id.suspend_card_view);
        suspendCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mSuspend), R.string.title_suspends, R.drawable.suspend));
            }
        });
        CardView connecterCardView = (CardView) v.findViewById(R.id.connector_card_view);
        connecterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mConnector), R.string.title_connecter, R.drawable.connecter));
            }
        });
        CardView panelCardView = (CardView) v.findViewById(R.id.panel_card_view);
        panelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mPanel), R.string.title_panels, R.drawable.panel));
            }
        });
        CardView screwConcreteCardView = (CardView) v.findViewById(R.id.screw_concrete_card_view);
        screwConcreteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mConcreteScrew), R.string.title_screw_on_concrete, R.drawable.screw_concrete));
            }
        });
        CardView screwWoodCardView = (CardView) v.findViewById(R.id.screw_wood_card_view);
        screwWoodCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mWoodScrew), R.string.title_self_tapping_screw, R.drawable.samorez));
            }
        });
        CardView screwDryWallCardWiew = (CardView) v.findViewById(R.id.screw_drywall_card_view);
        screwDryWallCardWiew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(detailToString(mDwScrew), R.string.title_screw_panel, R.drawable.screw_panel));
                Log.e(TAG, "first method");
            }
        });
        LinearLayout screwLayout = (LinearLayout) v.findViewById(R.id.Screw_Layout);
        if (mConcreteScrew.getCount() == 0) {
            screwLayout.removeView(screwConcreteCardView);
        }
        if (mWoodScrew.getCount() == 0) {
            screwLayout.removeView(screwWoodCardView);
        }
        if (mDwScrew.getCount() == 0) {
            screwLayout.removeView(screwDryWallCardWiew);
        }
        CardView screwPanelCardView = (CardView) v.findViewById(R.id.screw_panel_card_view);
        screwPanelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ImageDialog.getNewInstance(messageCreater.dwScrewsStringResult(), R.string.title_screw_panel, R.drawable.screw_panel));
                Log.e(TAG, "last method");
            }
        });

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.set_of_count_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_botton_set_menu:
                CeilingLab.getCeilingLab(getActivity()).deliteCeiling(mSuspendCeiling);
                ViewPager vp = (ViewPager) getActivity().findViewById(R.id.ceiling_pager);
                vp.getAdapter().notifyDataSetChanged();
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.send_button_set_menu:
                Intent intent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(createMessage())
                        .setSubject(getString(R.string.send_report))
                        .getIntent();
                intent = Intent.createChooser(intent, getString(R.string.send_report));
                startActivity(intent);
                return true;
            case R.id.rename_button_set_menu:
                getRenameDialog();
                return true;
            case R.id.new_button_set_menu:
                Intent intent2 = new Intent(getActivity(), CalculatorActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CardSetFragment.REQUEST_NAME) {
            String inputName = (String) data.getSerializableExtra(RenameDialog.EXTRA_NAME);
            mSuspendCeiling.setName(inputName);
            CeilingLab ceilings = CeilingLab.getCeilingLab(getActivity());
            ceilings.updateCeiling(mSuspendCeiling);
            mNameTextView.setText(mSuspendCeiling.getName());
        }
    }

    private void getRenameDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        RenameDialog dialog = RenameDialog.newInstance(mSuspendCeiling.getName());
        dialog.setTargetFragment(CardSetFragment.this, CardSetFragment.REQUEST_NAME);
        dialog.show(fm, CardSetFragment.RENAME_DIALOG);
    }

    private boolean isSizeEquals3(int size) {
        return !(size > 3000);
    }

    private boolean isOneLayer(int layerCount) {
        if (layerCount == 1)
            return true;
        return false;
    }

    public String detailToString(Detail detail) {
        return getResources().getQuantityString(R.plurals.plurals_piece, detail.getCount(), detail.getCount());
    }

    public String dwScrewsString(ScrewPanel screwPanel) {

        return messageCreater.dwScrewsStringResult();
    }


    private Detail foldDetails(Detail firstDetail, Detail secendDetail) {
        final int newcount = firstDetail.getCount() + secendDetail.getCount();
        Detail result = new Detail(newcount) {
            @Override
            public int calculateCount(int x, int y) {
                return newcount;
            }
        };
        return result;

    }


    private int chooseScrewTitle(Materials material) {
        switch (material) {
            case CONCRETE:
                return R.string.title_screw_on_concrete;
            case WOOD:
                return R.string.title_self_tapping_screw;
            case DRYWALL:
                return R.string.title_screw_panel;
            default:
                return R.string.title_screw_on_concrete;
        }
    }

    public void showDialog(ImageDialog dialog) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog.show(fm, IMAGE);
    }

    private String createMessage() {
        return messageCreater.createMessage();
    }

    private class CalculationMessageCreater {

        private SuspendCeiling mCeiling;

        private CalculationMessageCreater(SuspendCeiling ceiling) {
            mCeiling = ceiling;
        }

        private String createMessage() {
            String result = getString(R.string.message_string,
                    mCeiling.getName(),
                    cdStringResult(),
                    detailToString(mUd28),
                    detailToString(mSuspend),
                    detailToString(mLock),
                    panelStringResult(),
                    detailToString(mConnector),
                    screwsforProfileStringResult(),
                    dwScrewsStringResult());

            return result;
        }

        private String cdStringResult() {
            if (mCd60.getCdCountOf4Size().getCount() == 0) {
                String sizeString = isSizeEquals3(mCd60.getCdCountOf3Size().getCount()) ? getString(R.string.sub_title_size_3) : getString(R.string.sub_title_size_4);
                String countString = detailToString(mCd60);
                return sizeString + " - " + countString;
            } else {
                String firstLine = getString(R.string.sub_title_size_3) + " - " + detailToString(mCd60.getCdCountOf3Size());
                String secendLine = getString(R.string.sub_title_size_4) + " - " + detailToString(mCd60.getCdCountOf4Size());
                return firstLine + "\n" + secendLine;
            }
        }

        private String panelStringResult() {
            String sizeString = String.format(getString(R.string.format_panel_size_string), mSuspendCeiling.getPanelSize());
            return sizeString + " - " + detailToString(mPanel);
        }

        private String dwScrewsStringResult() {
            String firstLayerSize = getString(R.string.dw_screw_size_25mm) + " - ";
            if (mScrewPanel.getCount() != 0) {
                return firstLayerSize + detailToString(mScrewPanel);
            } else {
                return firstLayerSize + detailToString(mScrewPanel.getFirstLayerScrews()) + "\n" + getString(R.string.dw_screw_size_40mm) + " - " + detailToString(mScrewPanel.getSecondLayerScrews());
            }
        }

        private String screwsforProfileStringResult() {
            if (mCeiling.getWallMaterial() == mCeiling.getCeilingBaseMaterial()) {
                return getString(chooseScrewTitle(mCeilingBaseMaterial)) + " - " + detailToString(foldDetails(mScrewCeiling, mScrewWall));
            } else {
                return getString(chooseScrewTitle(mCeiling.getCeilingBaseMaterial())) + " - " + detailToString(mScrewCeiling) + "\n" + getString(chooseScrewTitle(mCeiling.getWallMaterial())) + " - " + detailToString(mScrewWall);
            }
        }
    }}
