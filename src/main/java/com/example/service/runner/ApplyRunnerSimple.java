package com.example.service.runner;

import com.example.service.SingleUrlApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyRunnerSimple implements ApplyRunner{
    private final SingleUrlApplyService singleService;




    @Override
    public void run(){
        List<String> urls = List.of(
                "https://sun9-29.userapi.com/impg/xTyfLi4Ym9P1E8H5t8gzxqGXPnsiDfo1AKlgCQ/NafVf2t2RWM.jpg?size=850x1071&quality=95&sign=579414b04b776b2f6c278096e0106523&type=album",
                "https://sun9-72.userapi.com/impg/fAwk5ufGrnouF4sk-pDaEtRS942nPhqF2xHEXg/Id0riH2s2bA.jpg?size=810x1080&quality=95&sign=fe54ede0a4e1e7aa75481c839662cb49&type=album",
                "https://sun9-68.userapi.com/impg/XUqdQD8mFHJ3pcB25MtFJHlO-nU1_ez3h3pzmA/5GjsAPWYdKE.jpg?size=482x595&quality=95&sign=1662a787f74a0a5a4405bb9cb03f55cc&type=album",
                "https://sun9-50.userapi.com/impg/m5KpG7GS5jXVkxzkv7keZRzsMHm_pAOBimZxXw/xn2y18pdsRY.jpg?size=555x1080&quality=95&sign=814abb0be258eb3e8231c97fcfaede6d&type=album",
                "https://sun9-58.userapi.com/impg/h-c_4Fc6280Uo8u6F-4oEf-Ori-2xGet8Q84eA/wbLTkPHpc48.jpg?size=789x1080&quality=95&sign=c5aec93029be4056528e97471ca90220&type=album",
                "https://sun9-78.userapi.com/impg/Z8_0tiXbYj6i0rNUFAaKb3meL1UIrwyWU1gPpA/wHZKc-c3H60.jpg?size=850x1080&quality=95&sign=98cf077b18678e5b756a4ce64fefefe6&type=album",
                "https://sun9-80.userapi.com/impg/Jy1wtYb4qMgYVIel86j3Kz7hdfV5sZKRQDzeFw/lpMMkmbQ7Dg.jpg?size=855x764&quality=95&sign=1f0f1fb563996bd07b989d9bb32a26d9&type=album",
                "https://sun9-20.userapi.com/impg/ucBo6qyyZgVnZG7Di63iwZJnCh5HRhmYQ8PWQw/lGSqwQhXUrw.jpg?size=821x1080&quality=95&sign=48ace9b024435fd1e8c079e07e3e34c2&type=album",
                "https://sun9-19.userapi.com/impg/mXBvF1YOce0TFSWeMLKoUNK83965OVAEAIVLRg/lwQxPl0jf2s.jpg?size=780x1080&quality=95&sign=348164ba489d9830d704614df82af950&type=album",
                "https://sun9-40.userapi.com/impg/xkGHfkvU2Xh1BDvQSG_uvLSYIesqM4Rq-HUy-Q/W6LfuxnKRA0.jpg?size=809x1080&quality=95&sign=692a8785f7a2343aa9e1e88e98449e12&type=album"
        );


        var results = singleService.applyAll(urls,new HashMap<>());


        System.out.println(results);



    }
}





