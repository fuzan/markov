package com.animallogic.proj.markov.text.fixture;

import java.util.Arrays;
import java.util.List;

public class Fixtures {
    private static final String SIMPLE = "i     love     cokecola, but    i    love     milk , and    he    love    jokes, but    i    personally " +
            "   love    cokecola, i    am   not  a    good   player   and   so  on";

    public static List<String> getContent() {
        String str = "comrades,\n\n\r\r\n" +
                "\n" +
                "When we talk about the first pillar, when it comes to the price of the device, one thing is very clear. The cost of a device can come down only when we are self-reliant, and you will remember that many people made fun of my talk of self-reliance. Till 2014, we used to import almost 100 per cent mobile phones, imported from abroad, and hence, we decided to become self-reliant in this sector. We increased mobile manufacturing units. In 2014 where there were only 2 mobile manufacturing units in the country, 2 8 years ago, now their number is above 200. We gave incentives to increase the production of mobile phones in India, encouraged the private sector. Today you are also seeing the expansion of this scheme in the PLI scheme. The results of these efforts were very positive. Today, India ranks number 2 in the world in mobile phone production. Not only this, till yesterday we used to import mobiles. Today we are exporting mobiles. sending to the world. Just imagine, from exporting zero mobile phones in 2014, today we have become a country exporting mobile phones worth thousands of crores, we have become an exporting country. Naturally, all these efforts have had an impact on the cost of the device. Now we have started getting more features at a lower cost.\n" +
                "\n\n" +
                "\n" +
                "\n" +
                "comrades,\n" +
                "\n" +
                "The second pillar we worked on after the device cost is digital connectivity. You also know that the real strength of the communication sector lies in connectivity. The more people connect, the better for the sector. If we talk about broadband connectivity, then in 2014 there were 60 million users. Today their number has increased to more than 80 crores. If we talk about the number of internet connections, then in 2014 where there were 250 million internet connections, today its number is reaching about 85 crores. It is also worth noting that today the number of internet users in our rural areas is increasing faster than the number of internet users in cities. And there is a special reason for this. In 2014, where optical fiber had reached less than 100 panchayats in the country, today optical fiber has reached more than one lakh 70 thousand panchayats. Now where is 100, where is one lakh 70 thousand. Like the government started a campaign to provide electricity to every household, like it worked on the mission of providing clean water to everyone through the Har Ghar Jal Abhiyan, like the gas cylinder delivered to the poorest of the poorest people through the Ujjwala scheme, like We had crores of people deprived of bank accounts. Crores of people who were not connected to the bank. After so many years of independence, the citizens of India were linked with the bank through Jan Dhan account. Similarly, our government is working on the goal of Internet for all.\n\n\n\n";
        return Arrays.asList(str.split(" "));
    }

    public static List<String> getSimpleContent() {
        return Arrays.asList(SIMPLE.split(" "));
    }

    public static String getSimpleContentRaw() {
        return SIMPLE;
    }
}
