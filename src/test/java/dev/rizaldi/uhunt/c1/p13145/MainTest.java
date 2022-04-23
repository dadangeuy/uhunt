package dev.rizaldi.uhunt.c1.p13145;

import dev.rizaldi.uhunt.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

public class MainTest {
    private final File directory = TestHelper.getDirectory(getClass());

    @Test
    @Timeout(1)
    public void sample() throws Exception {
        TestHelper.run(Main::main, directory, "sample");
    }

    @Test
    @Timeout(1)
    public void morass() throws Exception {
        TestHelper.run(Main::main, directory, "morass");
    }

    @Test
    public void decodeTitle() {
        String title = "Wuymul Wixcha";

        Solution solution = new Solution(title, 'g' - 'a');
        String decodedTitle = solution.encode();

        System.out.println(decodedTitle);
    }

    @Test
    public void decodeDescription() {
        String description = "Liguh ygjylil Dofcom Wuymul qum ihy iz nby zclmn alyun wbuluwnylm iz bcmnils ni yhwlsjn nby\n" +
            "gymmuaym ni bcm gcfcnuls wigguhxylm. Uwwilxcha ni bcmnilcuh Auciom Moynihcom: “cz by bux\n" +
            "uhsnbcha wihzcxyhncuf ni mus, by qliny cn ch wcjbyl, nbun cm, vs mi wbuhacha nby ilxyl iz nby\n" +
            "fynnylm iz nby ufjbuvyn, nbun hin u qilx wiofx vy guxy ion”.\n" +
            "Wuymul ufqusm omyx u mbczn iz 3 fynnylm ch nby ufjbuvyn ni nby lcabn. Guhs wyhnolcym\n" +
            "bupy jummyx mchwy nby ncgym iz Dofcom Wuymul, mi nby mwcyhwy iz yhwlsjncih bum guxy u\n" +
            "nlygyhxiom jlialymm. Zil yrugjfy, u lyguleuvfy lywyhn cgjlipygyhn wihmcmnm ch omcha xczzylyhn\n" +
            "mbczn pufoym, chmnyux iz ihfs 3.\n" +
            "Qlcny u jlialug nbun, acpyh uh chnyayl hogvyl, H, uhx u nyrn, yhwlsjnm cn vs omcha Wuymul\n" +
            "wixcha. Yhwlsjncih mbiofx vy xihy um ziffiqm. Fynnylm zlig ‘U’ ni ‘T’ bupy ni vy mbcznyx H jimcncihm\n" +
            "ni nby lcabn, wclwofulfs. Fiqylwumy fynnylm zlig ‘u’ ni ‘t’ ufmi bupy ni vy mbcznyx H jimcncihm ni\n" +
            "nby lcabn, wclwofulfs. Nby lymn iz wbuluwnylm xi hin bupy ni vy gixczcyx.";

        Solution solution = new Solution(description, 'g' - 'a');
        String decodedDescription = solution.encode();

        System.out.println(decodedDescription);
    }
}
