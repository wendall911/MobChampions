package mobchampions.platform;

import technology.roughness.whitenoise.platform.ServicesBase;

import mobchampions.MobChampions;
import mobchampions.platform.services.IPlatform;

public class Services extends ServicesBase {

    public static final IPlatform PLATFORM = load(MobChampions.LOGGER, IPlatform.class);
    
}
