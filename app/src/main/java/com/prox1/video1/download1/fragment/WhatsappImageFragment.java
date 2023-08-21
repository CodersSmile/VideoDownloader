package com.prox1.video1.download1.fragment;

import static androidx.databinding.DataBindingUtil.inflate;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.prox1.video1.download1.R;
import com.prox1.video1.download1.activity.FullViewActivity;
import com.prox1.video1.download1.activity.GalleryActivity;
import com.prox1.video1.download1.adapter.FileListAdapter;
import com.prox1.video1.download1.adapter.WhatsappStatusAdapter;
import com.prox1.video1.download1.databinding.FragmentHistoryBinding;
import com.prox1.video1.download1.databinding.FragmentWhatsappImageBinding;
import com.prox1.video1.download1.interfaces.FileListClickInterface;
import com.prox1.video1.download1.interfaces.FileListWhatsappClickInterface;
import com.prox1.video1.download1.model.WhatsappStatusModel;

import org.jetbrains.annotations.NotNull;

import static com.prox1.video1.download1.util.Utils.RootDirectoryLikeeShow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WhatsappImageFragment extends Fragment implements FileListWhatsappClickInterface {
    FragmentWhatsappImageBinding binding;

    private File[] allfiles;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;

    //#Permission
    LinearLayout sAccessBtn;
    ArrayList<WhatsappStatusModel> f = new ArrayList<>();
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_whatsapp_image, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {

        statusModelArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setOnRefreshListener(() -> {
            statusModelArrayList = new ArrayList<>();
            getData();
            binding.swiperefresh.setRefreshing(false);
        });

    }

    private void getData() {
        WhatsappStatusModel whatsappStatusModel;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
        File targetDirector = new File(targetPath);
        allfiles = targetDirector.listFiles();

        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allfilesBusiness = targetDirectorBusiness.listFiles();

        try {
            Arrays.sort(allfiles, (Comparator) (o1, o2) -> {
                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            });

            for (int i = 0; i < allfiles.length; i++) {
                File file = allfiles[i];
                if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                    whatsappStatusModel = new WhatsappStatusModel("WhatsStatus: " + (i + 1),
                            Uri.fromFile(file),
                            allfiles[i].getAbsolutePath(),
                            file.getName());
                    statusModelArrayList.add(whatsappStatusModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Arrays.sort(allfilesBusiness, (Comparator) (o1, o2) -> {
                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            });

            for (int i = 0; i < allfilesBusiness.length; i++) {
                File file = allfilesBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                    whatsappStatusModel = new WhatsappStatusModel("WhatsStatusB: " + (i + 1),
                            Uri.fromFile(file),
                            allfilesBusiness[i].getAbsolutePath(),
                            file.getName());
                    statusModelArrayList.add(whatsappStatusModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (statusModelArrayList.size() != 0) {
            binding.tvNoResult.setVisibility(View.GONE);
        } else {
            binding.tvNoResult.setVisibility(View.VISIBLE);
        }
        whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), statusModelArrayList);
        binding.rvFileList.setAdapter(whatsappStatusAdapter);

    }
    @Override
    public void getPosition(int i) {

    }
}
