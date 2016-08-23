package se.hellsoft.autovaluedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import se.hellsoft.autovaluedemo.models.MyAdapterFactory;
import se.hellsoft.autovaluedemo.models.Price;
import se.hellsoft.autovaluedemo.models.Transaction;

public class MainActivity extends AppCompatActivity {

    private Transaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTransaction = null;
        if (savedInstanceState == null) {
            try {
                Moshi moshi = new Moshi.Builder()
                        .add(new JsonDateAdapter())
                        .add(MyAdapterFactory.create())
                        .build();
                InputStream inputStream = getResources().openRawResource(R.raw.data);
                Source source = Okio.source(inputStream);
                BufferedSource bufferedSource = Okio.buffer(source);
                String json = bufferedSource.readUtf8();
                Log.d("AutoValue", "Raw JSON: " + json);
                JsonAdapter<Transaction> transactionJsonAdapter = moshi.adapter(Transaction.class);
                mTransaction = transactionJsonAdapter.fromJson(json);
            } catch (IOException e) {
                Log.e("AutoValue", "Error parsing JSON!", e);
            }
        } else {
            mTransaction = savedInstanceState.getParcelable("transaction");
        }

        Log.d("AutoValue", "Read: " + mTransaction);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("transaction", mTransaction);
    }

    private static class JsonDateAdapter {
        public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @FromJson
        public Date fromJson(String value) {
            try {
                return DATE_FORMAT.parse(value);
            } catch (ParseException e) {
                return null;
            }
        }

        @ToJson
        public String toJson(Date value) {
            return DATE_FORMAT.format(value);
        }
    }
}
