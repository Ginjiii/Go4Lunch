//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.test.core.app.ApplicationProvider;
//
//import com.example.go4lunch.goforlunch.models.Coworker;
//import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
//import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
//import com.example.go4lunch.goforlunch.ui.setting.SettingViewModel;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class SettingViewModelTest {
//
//    private SettingViewModel settingViewModel;
//    private Coworker coworker;
//
//    @Mock
//    private CoworkerRepository coworkerRepository;
//    @Mock
//    private SaveDataRepository saveDataRepository;
//
//    @Rule
//    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
//
//
//    @Before
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//        coworker = new Coworker("uid", "name", "email", "urlPhoto");
//        when(coworkerRepository.getActualUser()).thenReturn(coworker);
//        when(saveDataRepository.getNotificationSettings(coworker.getUid())).thenReturn(true);
//        settingViewModel = new SettingViewModel(coworkerRepository, saveDataRepository);
//    }
//
//    @Test
//    public void userInfo_correct(){
//        assertTrue(saveDataRepository.getNotificationSettings(coworker.getUid()));
//    }
//
//    @Test
//    public void disableNotification_disable(){
//        settingViewModel.notificationStateChanged(false);
//        assertFalse(settingViewModel.getStatusNotification().getValue());
//        verify(saveDataRepository).saveNotificationSettings(false, coworker.getUid());
//    }
//
//    @Test
//    public void enableNotification_enable(){
//        settingViewModel.notificationStateChanged(true);
//        assertTrue(settingViewModel.getStatusNotification(ApplicationProvider.getApplicationContext()));
//        verify(saveDataRepository).saveNotificationSettings(true, coworker.getUid());
//    }
//}
