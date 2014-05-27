package thompson.eric.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import thompson.eric.factories.QuestionSetFactoryImpl;
import thompson.eric.flashcards.R;
import thompson.eric.question.Question;
import thompson.eric.set.QuestionSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	private QuestionSetFactoryImpl factory; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		factory = new QuestionSetFactoryImpl();

		// Find the ListView resource. 
		mainListView = (ListView) findViewById(R.id.mainlistview);
		
		//XML
		PreferenceManager.setDefaultValues(this, R.layout.initial_prefs, false);
		SharedPreferences prefs = this.getSharedPreferences("master_exercise_list", Context.MODE_PRIVATE);
		String check = prefs.getString("PreferenceExist", "notExist");
		if(check.equals("notExist")){
			SharedPreferences.Editor editor = prefs.edit();
			Map<String,?> keys = PreferenceManager.getDefaultSharedPreferences(this).getAll();
			for(Map.Entry<String, ?> entry: keys.entrySet()){
				editor.putString(entry.getKey(),(String)entry.getValue());
			}
			editor.commit();
		}
		
		final ArrayList<QuestionSet> questionSets = new ArrayList<QuestionSet>();
		questionSets.add(factory.createBlankQuestionSet());
		
		final ArrayList<String> questionSetNames = new ArrayList<String>();
		questionSetNames.add("Create New Set...");

		// Create ArrayAdapter using the workout list.
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, questionSetNames);

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);
		mainListView.setVerticalScrollBarEnabled(true);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				if(position == 0) {
					// "Create New Set" was clicked   
					Intent intent = new Intent(view.getContext(), CreateNewSetActivity.class);
					startActivity(intent);
				} else {
					// Anything else was clicked
					//Bundle b = new Bundle();
					//ArrayList<Question> questions = new ArrayList<Question>();
					//questions = questionSets.get(position).getAllQuestions();
					//b.putParcelableArrayList(questionSetNames.get(position), questionSets.get(position).getAllQuestions());
					Intent intent = new Intent(MainActivity.this, StudyActivity.class);
					intent.putExtra("QuestionSet", questionSets.get(position).getAllQuestions());
					startActivity(intent);
				}                 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void fillQuestionSets() {
		
	}
	
    public static void writeObjectToFile(Context context, Object object, String filename) {

        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
    
    public static Object readObjectFromFile(Context context, String filename) {

        ObjectInputStream objectIn = null;
        Object object = null;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return object;
    }
}
