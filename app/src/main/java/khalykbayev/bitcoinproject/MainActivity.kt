package khalykbayev.bitcoinproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.firebase.auth.FirebaseAuth
import khalykbayev.bitcoinproject.Adapter.TransactionListAdapter
import khalykbayev.bitcoinproject.Auth.AuthActivity
import khalykbayev.bitcoinproject.Converter.ConverterFragment
import khalykbayev.bitcoinproject.ExchangeRates.ExchangeRates
import khalykbayev.bitcoinproject.TransactionList.TransactionList


class MainActivity : BaseActivity() {

    private var transactionListFragment = TransactionList()
    private var converterFragment : Fragment = ConverterFragment()
    private var exchangeRatesFragment : Fragment = ExchangeRates()
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar_main_activity)
        toolbar.inflateMenu(R.menu.navbar_menu)
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.disableShiftMode()
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, exchangeRatesFragment).hide(exchangeRatesFragment)
            .add(R.id.frame_layout, transactionListFragment).hide(transactionListFragment)
            .add(R.id.frame_layout, converterFragment).hide(converterFragment)
            .commit()

        showItem(R.id.navigation_exchange_rates)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            showItem(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun showItem(itemId: Int) {
        supportFragmentManager.beginTransaction()
            .hide(transactionListFragment)
            .hide(converterFragment)
            .hide(exchangeRatesFragment)
            .commit()
        toolbar.menu.clear()

        when (itemId) {
            R.id.navigation_exchange_rates -> {
                toolbar.setTitle(R.string.title_exchange_rates)
                supportFragmentManager.beginTransaction().show(exchangeRatesFragment).commit()
                toolbar.inflateMenu(R.menu.navbar_menu)
            }
            R.id.navigation_transaction_list -> {
                toolbar.setTitle(R.string.title_transaction_list)
                supportFragmentManager.beginTransaction().show(transactionListFragment).commit()
                toolbar.inflateMenu(R.menu.menu_transaction_list)
            }
            R.id.navigation_converter -> {
                toolbar.setTitle(R.string.title_converter)
                supportFragmentManager.beginTransaction().show(converterFragment).commit()
                toolbar.inflateMenu(R.menu.navbar_menu)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_logout -> {
                val intent = Intent(this, AuthActivity::class.java)
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
            }
            R.id.toolbar_transaction_list_refresh -> {
                transactionListFragment.refresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    @SuppressLint("RestrictedApi")
    fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShifting(false)
                item.setChecked(item.itemData.isChecked)
            }
        }
        catch (e: NoSuchFieldException) {}
        catch (e: IllegalStateException) {}
    }

}
