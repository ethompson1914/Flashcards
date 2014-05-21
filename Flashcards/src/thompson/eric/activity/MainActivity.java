package thompson.eric.activity;

import java.util.ArrayList;
import java.util.Map;

import thompson.eric.flashcards.R;
import thompson.eric.question.Question;
import thompson.eric.set.QuestionSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
					Bundle b = new Bundle();
					ArrayList<Question> questions = new ArrayList<Question>();
					questions = questionSets.get(position).getAllQuestions();
					b.putStringArray("HL", new String[] { exercises.get(0), exercises.get(1), exercises.get(2), exercises.get(3) });
					Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
					intent.putExtras(b);
					startActivity(intent);
				}                 
			}
		});     

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

}
