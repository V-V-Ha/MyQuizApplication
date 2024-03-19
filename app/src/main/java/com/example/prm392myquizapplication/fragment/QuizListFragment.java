package com.example.prm392myquizapplication.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392myquizapplication.QuizManagementActivity;
import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.adapter.SubjectAdapter;
import com.example.prm392myquizapplication.data.Subject;
import com.example.prm392myquizapplication.data.UserDatabase;
import com.example.prm392myquizapplication.data.UserDatabaseClient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText ptSubjectName;
    private RecyclerView rvSelectedSubject;

    public QuizListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizListFragment newInstance(String param1, String param2) {
        QuizListFragment fragment = new QuizListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"StaticFieldLeak", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        rvSelectedSubject = rootView.findViewById(R.id.rv_subject_list);

        rootView.findViewById(R.id.btn_add_subject).setOnClickListener(view -> {
            // Tạo một Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Add Subject");

            // Tạo một EditText để người dùng nhập tên mới cho Subject
            final EditText input = new EditText(requireContext());
            builder.setView(input);

            // Thiết lập nút Add
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String subjectName = input.getText().toString();
                    if (!subjectName.isEmpty()) {
                        // Tạo một Subject mới và thêm vào cơ sở dữ liệu
                        Subject subject = new Subject(subjectName);
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                try {
                                    UserDatabaseClient.getInstance(requireContext()).subjectDao().insertSubject(subject);
                                    requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Add new Subject successfully!!!", Toast.LENGTH_SHORT).show());
                                } catch (Exception ex) {
                                    requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Add new Subject failed!!!", Toast.LENGTH_SHORT).show());
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                new GetAllSelectedSubjectTask().execute();
                            }
                        }.execute();
                    } else {
                        Toast.makeText(requireContext(), "Subject Name is required!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Thiết lập nút Cancel
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Hiển thị Dialog
            builder.show();
        });

        new GetAllSelectedSubjectTask().execute();

        return rootView;
    }


    @SuppressLint("StaticFieldLeak")
    class GetAllSelectedSubjectTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Subject> subjects = new ArrayList<>();

        public GetAllSelectedSubjectTask() {
        }

        //Thuc hien viec lay toan bo subject duoi nen
        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(requireContext());
            subjects = (ArrayList<Subject>) databaseClient.subjectDao().getAllSubjects();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SubjectAdapter adapter = new SubjectAdapter(subjects, UserDatabaseClient.getInstance(requireContext()).subjectDao());

            adapter.setOnUpdateClickListener(new SubjectAdapter.OnUpdateClickListener() {
                @Override
                public void onUpdateClick(Subject subject) {
                    // Tạo một Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Update Subject");

                    // Tạo một EditText để người dùng nhập tên mới cho Subject
                    final EditText input = new EditText(requireContext());
                    input.setText(subject.getSubjectName());
                    builder.setView(input);

                     // Xử lý sự kiện khi nhấn nút Update
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newSubjectName = input.getText().toString();
                            if (!newSubjectName.isEmpty()) {
                                // Cập nhật Subject trong cơ sở dữ liệu
                                subject.setSubjectName(newSubjectName);
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        try {
                                            //Thuc hien update subjetc duoi nen
                                            UserDatabaseClient.getInstance(requireContext()).subjectDao().updateSubject(subject);
                                            requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Update Subject successfully!!!", Toast.LENGTH_SHORT).show());

                                        } catch (Exception ex) { //Neu xay ra loi trong viec xu ly DB
                                            requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Update Subject failed!!!", Toast.LENGTH_SHORT).show());
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        // Cập nhật RecyclerView sau khi cập nhật Subject: Khoi chay lai GetAllSelectedSubjectTask
                                        new GetAllSelectedSubjectTask().execute();
                                    }
                                }.execute();
                            }
                        }
                    });

                    // Thiết lập nút Cancel
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Khi click vao Cancel thi an dialog
                            dialog.cancel();
                        }
                    });

                    // Hiển thị Dialog
                    builder.show();
                }
            });
            //Thiet lap on click cho moi adapter
            adapter.setOnDeleteClickListener(new SubjectAdapter.OnDeleteClickListener() {
                @Override
                public void onDeleteClick(Subject subject) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Delete Subject")
                            .setMessage("Are you sure you want to delete this subject?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    new AsyncTask<Void, Void, Void>() {
                                        @Override
                                        protected Void doInBackground(Void... voids) {
                                            try {
                                                UserDatabaseClient.getInstance(requireContext()).subjectDao().deleteSubject(subject);
                                                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Delete Subject successfully!!!", Toast.LENGTH_SHORT).show());
                                            } catch (Exception ex) {
                                                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Delete Subject failed!!!", Toast.LENGTH_SHORT).show());
                                            }
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            super.onPostExecute(aVoid);
                                            new GetAllSelectedSubjectTask().execute();
                                        }
                                    }.execute();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            });

            adapter.setOnSubjectClickListener(new SubjectAdapter.OnSubjectClickListener() {
                @Override
                public void onSubjectClick(Subject subject) {
                    // Tao 1 intent tu this context sang QuizManagementActivity
                    Intent intent = new Intent(requireContext(), QuizManagementActivity.class);
                    //Thiet lap du lieu cho Intent
                    intent.putExtra("subject_id", subject.getSubjectID());
                    intent.putExtra("subject_name", subject.getSubjectName());
                    //Khoi chay Intent
                    startActivity(intent);
                }
            });
            rvSelectedSubject.setAdapter(adapter);
        }
    }
}