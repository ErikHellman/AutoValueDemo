package se.hellsoft.autovaluedemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.ryanharter.auto.value.parcel.TypeAdapter;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.Date;

@AutoValue
public abstract class Transaction implements Parcelable {

    public static Transaction create(long id, String name, Date timestamp, Price price) {
        return new AutoValue_Transaction(id, name, timestamp, price);
    }

    public static JsonAdapter<Transaction> jsonAdapter(Moshi moshi) {
        return new AutoValue_Transaction.MoshiJsonAdapter(moshi);
    }

    public abstract long id();

    public abstract String name();

    public abstract Date timestamp();

    public abstract Price price();

    public class DateTypeAdapter implements TypeAdapter<Date> {
        public Date fromParcel(Parcel in) {
            return new Date(in.readLong());
        }

        public void toParcel(Date value, Parcel dest) {
            dest.writeLong(value.getTime());
        }
    }


}
