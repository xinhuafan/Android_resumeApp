package com.android.alice.resume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.alice.resume.model.Education;
import com.android.alice.resume.util.DateUtils;

import java.util.Arrays;

import static com.android.alice.resume.util.DateUtils.dateToString;

/**
 * Created by xinhuafan on 12/19/16.
 */

@SuppressWarnings("ConstantConditions")
public class EducationEditActivity extends EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    @Override
    protected int getLayoutId() {
        return R.layout.education_edit_item;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Education data) {
        ((EditText)findViewById(R.id.education_edit_school)).setText(data.school);
        ((EditText)findViewById(R.id.education_edit_major)).setText(data.major);
        ((EditText)findViewById(R.id.education_edit_start_date)).setText(dateToString(data.startDate));
        ((EditText)findViewById(R.id.education_edit_end_date)).setText(dateToString(data.endDate));
        ((EditText)findViewById(R.id.education_edit_course)).setText(TextUtils.join("\n", data.courses));

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void saveAndExit(@Nullable Education data) {
        if (data == null) {
            data = new Education();
        }

        data.school = ((EditText)findViewById(R.id.education_edit_school)).getText().toString();
        data.major = ((EditText)findViewById(R.id.education_edit_major)).getText().toString();
        data.startDate = DateUtils.stringToDate(((EditText)findViewById(R.id.education_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(((EditText)findViewById(R.id.education_edit_end_date)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(((EditText)findViewById(R.id.education_edit_course)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }
}
